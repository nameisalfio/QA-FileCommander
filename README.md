# Cognitive File Commander 🚀

[![Java Version](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

Un'applicazione Spring Boot avanzata per la gestione di questionari tecnici e la consultazione di file di codice tramite modelli linguistici locali (LLM) come Llama, integrati con Ollama.

## ✨ Funzionalità Principali
- **Gestione Domande via LLM**  
  Interagisci con il file di domande usando linguaggio naturale.
- **Consultazione di File di Codice**  
  Analizza file di codice sorgente (es. `Player.java`) per ottenere riepiloghi, spiegazioni e suggerimenti.
- **Operazioni CRUD Intelligenti**  
  Aggiungi, modifica o elimina domande e contenuti tramite comandi testuali.
- **Interfaccia da Terminale**  
  Controlla l'app direttamente dal terminale, senza interfaccia grafica.
- **Integrazione con Ollama**  
  Supporto per diversi modelli linguistici (Llama2, Mistral, ecc.), eseguiti in locale.

## 🛠️ Tecnologie Utilizzate
- **Backend**: Spring Boot 3.5 + WebFlux
- **LLM Integration**: Ollama API (modelli locali)
- **Language**: Java 21+
- **Build Tool**: Maven
- **Utilities**: Lombok, Jackson

## 🚀 Come Iniziare

### Prerequisiti
- Java 21+ ([Installazione](https://adoptium.net/))
- Ollama in esecuzione ([Guida Installazione](https://ollama.ai/))
- Maven 3.9+

### Installazione
```bash
git clone https://github.com/tuo-utente/qa-file-commander.git
cd qa-file-commander
mvn clean install
```

### Avvio Applicazione
Assicurati che Ollama sia in esecuzione:
```bash
ollama run ollama-3.2
```

Avvia l'applicazione Spring Boot:
```bash
mvn spring-boot:run
```

### Esempio di Utilizzo
Cognitive File Commander permette di consultare file di codice come `Player.java`, estraendone informazioni chiave tramite LLM:
```bash
> Analizza la classe Player
```

### Struttura del Progetto
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

## Licenza
Distribuito sotto licenza MIT. Vedi LICENSE per maggiori dettagli.

