package io.cognitiveforge.qa_file_commander.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public Operation parse(String jsonResponse) throws JsonProcessingException {
        return mapper.readValue(jsonResponse, Operation.class);
    }
    
    public class Operation {
        private String action;
        private Integer index;
        private String question;
    
        // Getter e Setter per `action`
        public String getAction() {
            return action;
        }
    
        public void setAction(String action) {
            this.action = action;
        }
    
        // Getter e Setter per `index`
        public Integer getIndex() {
            return index;
        }
    
        public void setIndex(Integer index) {
            this.index = index;
        }
    
        // Getter e Setter per `question`
        public String getQuestion() {
            return question;
        }
    
        public void setQuestion(String question) {
            this.question = question;
        }
    }
}