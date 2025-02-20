package io.cognitiveforge.qa_file_commander;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.cognitiveforge.qa_file_commander.service.TextFileService;

@Component
public class TerminalInterface implements CommandLineRunner {

    private final TextFileService textFileService;

    public TerminalInterface(TextFileService textFileService) {
        this.textFileService = textFileService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("💬 QA File Commander Avviato!");
        
        while (true) {
            System.out.print("\n🔹 Scrivi un comando (es. 'domanda 4', 'lista', 'esci'): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.startsWith("domanda ")) {
                try {
                    int index = Integer.parseInt(input.replace("domanda ", "").trim());
                    System.out.println(textFileService.getQuestion(index));
                } catch (NumberFormatException e) {
                    System.out.println("❌ Formato non valido! Usa 'domanda 4'.");
                }
            } else if (input.equals("lista")) {
                System.out.println("📜 Tutte le domande:");
                textFileService.getAllQuestions().forEach(System.out::println);
            } else if (input.equals("esci")) {
                System.out.println("👋 Chiusura...");
                break;
            } else {
                System.out.println("❓ Comando non riconosciuto.");
            }
        }

        scanner.close();
    }
}
