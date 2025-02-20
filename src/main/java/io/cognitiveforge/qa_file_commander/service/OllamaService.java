package io.cognitiveforge.qa_file_commander.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OllamaService {

    private static final String FILE_PATH = "questions.txt";
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public String processUserQuery(String userQuery) {
        // Se l'utente vuole aggiungere o modificare una domanda, gestiscilo separatamente
        if (userQuery.toLowerCase().startsWith("aggiungi")) {
            String newQuestion = userQuery.substring("aggiungi".length()).trim();
            appendToFile(newQuestion);
            return "Domanda aggiunta con successo!";
        }

        if (userQuery.toLowerCase().startsWith("modifica")) {
            String newContent = userQuery.substring("modifica".length()).trim();
            try {
                Files.write(Path.of(FILE_PATH), newContent.getBytes());
                return "File modificato con successo!";
            } catch (IOException e) {
                return "Errore durante la modifica del file: " + e.getMessage();
            }
        }

        // Altrimenti, delega completamente la risposta al modello LLM
        String fileContent = readFileContent();
        String prompt = """
            Il seguente testo contiene un elenco di domande:
            
            %s
            
            L'utente ha chiesto: "%s"
            Rispondi in modo appropriato, considerando il contesto delle domande e la richiesta dell'utente. 

            L'utente potrebbe chiederti di effettuare operazioni di ricerca o di manipolazione del testo, se individui l'intenzione di leggere 
            la domanda X, leggi pure la riga X del testo. 
            
            Se la richiesta dell'utente è una domanda di carattere generale rispondi senza consultare il testo (es. se l'utente 
            chiede "Qual è la capitale dell'Italia?" rispondi "Roma").
            """.formatted(fileContent, userQuery);

        return callOllamaModel(prompt);
    }

    private String readFileContent() {
        try {
            return Files.readString(Path.of(FILE_PATH));
        } catch (IOException e) {
            return "Errore nella lettura del file.";
        }
    }

    private void appendToFile(String newContent) {
        try {
            Path filePath = Path.of(FILE_PATH);
    
            // Crea il file se non esiste
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
    
            // Aggiungi la nuova domanda direttamente al file
            Files.write(filePath, (newContent + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Errore durante l'aggiunta al file: " + e.getMessage());
        }
    }

    private String callOllamaModel(String prompt) {
        try {
            // Creiamo la connessione HTTP
            URL url = new URL(OLLAMA_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Creiamo il JSON da inviare
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(new OllamaRequest("llama3.2", prompt));

            // Scriviamo il JSON nel body della richiesta
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leggiamo la risposta
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Estrarre solo il campo "response" dal JSON di output
            return objectMapper.readTree(response.toString()).get("response").asText();

        } catch (Exception e) {
            return "Errore nella comunicazione con Ollama: " + e.getMessage();
        }
    }

    // Classe per la richiesta JSON
    private static class OllamaRequest {
        public String model;
        public String prompt;
        public boolean stream = false;

        public OllamaRequest(String model, String prompt) {
            this.model = model;
            this.prompt = prompt;
        }
    }
}