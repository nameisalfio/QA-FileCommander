package io.cognitiveforge.qa_file_commander.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class TextFileService {
    private final Path filePath = Paths.get("questions.txt");
    
    public List<String> readQuestions() throws IOException {
        if (!Files.exists(filePath)) Files.createFile(filePath);
        return Files.readAllLines(filePath);
    }
    
    public void writeQuestions(List<String> questions) throws IOException {
        List<String> formatted = IntStream.range(0, questions.size())
                .mapToObj(i -> (i + 1) + ") " + questions.get(i))
                .collect(Collectors.toList());
        Files.write(filePath, formatted, StandardOpenOption.TRUNCATE_EXISTING);
    }
}