package io.cognitiveforge.qa_file_commander;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.cognitiveforge.qa_file_commander.service.OllamaService;
import io.cognitiveforge.qa_file_commander.service.TextFileService;
import io.cognitiveforge.qa_file_commander.util.ResponseParser;
import io.cognitiveforge.qa_file_commander.util.ResponseParser.Operation;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(OllamaService ollamaService, TextFileService textFileService, ResponseParser parser) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n> ");
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Uscita dall'applicazione...");
                    break;
                }

                try {
                    // Leggi le domande esistenti
                    List<String> questions = textFileService.readQuestions();
                    String context = String.join("\n", questions);

                    // Invia il comando a Ollama
                    String response = ollamaService.processCommand(command, context);
                    ResponseParser.Operation op = parser.parse(response);

                    // Esegui l'operazione (aggiungi/modifica/elimina)
                    processOperation(op, questions);
                    textFileService.writeQuestions(questions);

                    System.out.println("Operazione completata!");
                } catch (Exception e) {
                    System.err.println("Errore: " + e.getMessage());
                }
            }
            scanner.close();
        };
    }

    private void processOperation(Operation op, List<String> questions) {
        switch (op.getAction().toLowerCase()) {
            case "add":
                if (op.getQuestion() != null && !op.getQuestion().isEmpty()) {
                    questions.add(op.getQuestion());
                    System.out.println("Domanda aggiunta: " + op.getQuestion());
                } else {
                    System.err.println("Errore: Testo della domanda mancante per l'operazione 'add'.");
                }
                break;

            case "edit":
                if (op.getIndex() != null && op.getIndex() > 0 && op.getIndex() <= questions.size()) {
                    if (op.getQuestion() != null && !op.getQuestion().isEmpty()) {
                        questions.set(op.getIndex() - 1, op.getQuestion());
                        System.out.println("Domanda modificata: " + op.getQuestion());
                    } else {
                        System.err.println("Errore: Testo della domanda mancante per l'operazione 'edit'.");
                    }
                } else {
                    System.err.println("Errore: Indice non valido per l'operazione 'edit'.");
                }
                break;

            case "delete":
                if (op.getIndex() != null && op.getIndex() > 0 && op.getIndex() <= questions.size()) {
                    String removedQuestion = questions.remove(op.getIndex() - 1);
                    System.out.println("Domanda eliminata: " + removedQuestion);
                } else {
                    System.err.println("Errore: Indice non valido per l'operazione 'delete'.");
                }
                break;

            case "list":
                if (questions.isEmpty()) {
                    System.out.println("Nessuna domanda presente.");
                } else {
                    System.out.println("Domande presenti:");
                    for (int i = 0; i < questions.size(); i++) {
                        System.out.println((i + 1) + ") " + questions.get(i));
                    }
                }
                break;

            default:
                System.err.println("Errore: Azione non riconosciuta: " + op.getAction());
                break;
        }
    }
}