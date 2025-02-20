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

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OllamaService {

    private static final String FILE_PATH = "questions.txt";
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public String processUserQuery(String userQuery) {
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
