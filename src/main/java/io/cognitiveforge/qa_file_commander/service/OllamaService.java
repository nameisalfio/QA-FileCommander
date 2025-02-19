package io.cognitiveforge.qa_file_commander.service;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaService {
    private final WebClient webClient = WebClient.create("http://localhost:11434");

    public String processCommand(String prompt, String context) {
        String fullPrompt = "Contesto:\n" + context + "\n\nComando: " + prompt + "\nRispondi in JSON:";
        
        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                    "model", "llama2",
                    "prompt", fullPrompt,
                    "format", "json",
                    "stream", false
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}