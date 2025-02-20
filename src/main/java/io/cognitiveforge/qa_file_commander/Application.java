package io.cognitiveforge.qa_file_commander;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

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
    public void run(String... args) {
        System.out.println("💬 Benvenuto! Puoi fare domande sul file di testo.");
        Scanner scanner = new Scanner(System.in);

        // Legge il contenuto del file all'inizio
        String filePath = "questions.txt";
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(filePath));
        } catch (Exception e) {
            System.err.println("❌ Errore nella lettura del file: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.print("\n🔹 Scrivi una domanda o 'esci': ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("esci")) {
                System.out.println("👋 Uscita dal sistema.");
                break;
            }

            // Chiede a Ollama di rispondere basandosi sul file di testo
            String response = ollamaService.processUserQuery(userInput);
            System.out.println("\n🤖 Risposta: " + response);
        }

        scanner.close();
    }
}
