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
        // Se l'utente dice "Ciao", non rispondere alle domande
        if (userQuery.equalsIgnoreCase("ciao")) {
            return "Ciao! Come posso aiutarti?";
        }

        // Se l'utente vuole aggiungere una domanda
        if (userQuery.toLowerCase().startsWith("aggiungi")) {
            String newQuestion = userQuery.substring("aggiungi".length()).trim();
            appendToFile(newQuestion);
            return "Domanda aggiunta con successo!";
        }

        // Se l'utente vuole modificare il file
        if (userQuery.toLowerCase().startsWith("modifica")) {
            String newContent = userQuery.substring("modifica".length()).trim();
            try {
                Files.write(Path.of(FILE_PATH), newContent.getBytes());
                return "File modificato con successo!";
            } catch (IOException e) {
                return "Errore durante la modifica del file: " + e.getMessage();
            }
        }

        // Altrimenti, elabora la query normalmente
        String fileContent = readFileContent();
        String prompt = """
            Il seguente testo contiene un elenco di domande:
            
            %s
            
            L'utente ha chiesto: "%s"
            Rispondi basandoti esclusivamente sul contenuto del testo. Se possibile, dai una risposta diretta. Se non trovi la risposta, dì chiaramente che non è presente.
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
    
            // Leggi tutte le righe del file
            var lines = Files.readAllLines(filePath);
    
            // Trova l'ultima riga con un numero progressivo
            int lastNumber = 0;
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = lines.get(i);
                if (line.trim().isEmpty()) {
                    continue; // Salta le righe vuote
                }
    
                var matcher = java.util.regex.Pattern.compile("^(\\d+)\\)").matcher(line);
                if (matcher.find()) {
                    lastNumber = Integer.parseInt(matcher.group(1));
                    break; // Esci dal ciclo una volta trovato l'ultimo numero
                }
            }
    
            // Incrementa il numero progressivo
            int newNumber = lastNumber + 1;
    
            // Aggiungi la nuova domanda con il numero progressivo
            String newQuestion = newNumber + ") " + newContent;
            Files.write(filePath, (newQuestion + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
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