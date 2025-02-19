package io.cognitiveforge.qa_file_commander.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Component
public class ResponseParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public Operation parse(String jsonResponse) throws JsonProcessingException {
        JsonNode root = mapper.readTree(jsonResponse);
        return mapper.treeToValue(root.path("response"), Operation.class);
    }

    @Data
    public static class Operation {
        private String action;
        private Integer index;
        private String question;
    }
}