package io.cognitiveforge.qa_file_commander;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import io.cognitiveforge.qa_file_commander.service.OllamaService;

@Component
public class TerminalInterface {

    private final OllamaService ollamaService;

    public TerminalInterface(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("💬 Benvenuto! Puoi fare domande sul file di testo.");

        while (true) {
            System.out.print("\n🔹 Scrivi una domanda o 'esci': ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("esci")) {
                System.out.println("👋 Uscita dal sistema.");
                break;
            }

            String response = ollamaService.processUserQuery(userInput);
            System.out.println("🤖 " + response);
        }
        scanner.close();
    }
}
