# Cognitive File Commander 🚀

[![Java Version](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

An advanced Spring Boot application for managing technical questionnaires and consulting code files using local language models (LLMs) like Llama, integrated with Ollama.

## ✨ Key Features
- **Question Management via LLM**  
  Interact with the question file using natural language.
- **Code File Consultation**  
  Analyze source code files (e.g., `Player.java`) to obtain summaries, explanations, and suggestions.
- **Intelligent CRUD Operations**  
  Add, modify, or delete questions and content via text commands.
- **Terminal-Based Interface**  
  Control the app directly from the terminal without a graphical interface.
- **Ollama Integration**  
  Support for various language models (Llama2, Mistral, etc.), running locally.

## 🛠️ Technologies Used
- **Backend**: Spring Boot 3.5 + WebFlux
- **LLM Integration**: Ollama API (local models)
- **Language**: Java 21+
- **Build Tool**: Maven
- **Utilities**: Lombok, Jackson

## 🚀 Getting Started

### Prerequisites
- Java 21+ ([Installation](https://adoptium.net/))
- Ollama running ([Installation Guide](https://ollama.ai/))
- Maven 3.9+

### Installation
```bash
git clone https://github.com/your-username/qa-file-commander.git
cd qa-file-commander
mvn clean install
```

### Running the Application
Ensure Ollama is running:
```bash
ollama run ollama-3.2
```

Start the Spring Boot application:
```bash
mvn spring-boot:run
```

### Example Usage
Cognitive File Commander allows you to consult code files like `Player.java`, extracting key information using LLM:
```bash
> Analyze the Player class
```

### Project Structure
```bash
QA-FileCommander/
├── HELP.md
├── LICENSE
├── Player.java
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── questions.txt
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── config
│   │   │   ├── controller
│   │   │   ├── io
│   │   │   │   └── cognitiveforge
│   │   │   │       └── qa_file_commander
│   │   │   │           ├── Application.java
│   │   │   │           ├── TerminalInterface.java
│   │   │   │           ├── service
│   │   │   │           │   ├── OllamaService.java
│   │   │   │           │   └── TextFileService.java
│   │   │   │           └── util
│   │   │   │               └── ResponseParser.java
│   │   │   ├── model
│   │   │   └── util
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── io
│               └── cognitiveforge
│                   └── qa_file_commander
│                       └── CognitiveFileCommanderApplicationTests.java
└── target
```

## License
Distributed under the MIT License. See LICENSE for more details.
