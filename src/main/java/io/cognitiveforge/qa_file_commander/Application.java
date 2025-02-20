package io.cognitiveforge.qa_file_commander;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cognitiveforge.qa_file_commander.service.OllamaService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final OllamaService ollamaService;

    public Application(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("questions.txt"));

        for (String question : lines) {
            if (!question.trim().isEmpty()) {
                System.out.println("📝 Domanda: " + question);
                String response = ollamaService.askLlama(question);
                System.out.println("\n🤖 Risposta: " + response);
            }
        }
    }
}
