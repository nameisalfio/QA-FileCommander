package io.cognitiveforge.qa_file_commander.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaService {
    private final WebClient webClient;

    public OllamaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:11434").build();
    }

    public String askLlama(String question) {
        String requestBody = "{ \"model\": \"llama3.2\", \"prompt\": \"" + question + "\" }";

        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractResponse)  // 💡 Nuova funzione per pulire la risposta
                .block();  
    }

    // 📌 Estrae solo il contenuto utile dalla risposta JSON di Ollama
    private String extractResponse(String rawJson) {
        int start = rawJson.indexOf("\"response\":\"") + 11;
        int end = rawJson.indexOf("\",\"done\":");
        if (start > 10 && end > start) {
            return rawJson.substring(start, end);
        }
        return "⚠️ Errore nel parsing della risposta!";
    }
}
