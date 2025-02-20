package io.cognitiveforge.qa_file_commander.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TextFileService {
    
    private List<String> questions;

    public TextFileService() {
        loadQuestions();
    }

    private void loadQuestions() {
        try {
            Path path = Paths.get("questions.txt"); // Percorso corretto in Spring Boot
            questions = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println("✅ Domande caricate: " + questions.size());
        } catch (Exception e) {
            e.printStackTrace();
            questions = List.of("⚠️ Errore: Impossibile caricare le domande.");
        }
    }

    public String getQuestion(int index) {
        if (index >= 1 && index <= questions.size()) {
            return "🔹 Domanda " + index + ": " + questions.get(index - 1);
        }
        return "❌ Domanda non trovata.";
    }

    public List<String> getAllQuestions() {
        return questions;
    }
}
