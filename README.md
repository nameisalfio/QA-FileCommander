# Cognitive File Commander 🚀

[![Java Version](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

Un'applicazione Spring Boot intelligente per la gestione di questionari tecnici via terminale, integrata con modelli linguistici (LLM) tramite Ollama.

## ✨ Funzionalità Principali
- **Gestione Domande via LLM**  
  Interagisci con il file di domande usando linguaggio naturale
- **Operazioni CRUD Intelligenti**  
  Aggiungi, modifica o elimina domande attraverso comandi testuali
- **Interfaccia da Terminale**  
  Interazione diretta senza bisogno di interfaccia web
- **Integrazione con Ollama**  
  Supporto per diversi modelli linguistici (Llama2, Mistral, ecc.)

## 🛠️ Tecnologie Utilizzate
- **Backend**: Spring Boot 3.5 + WebFlux
- **LLM Integration**: Ollama API
- **Language**: Java 17
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

```bash
mvn spring-boot:run

# Verifica che Ollama sia in esecuzione
curl http://localhost:11434
```

### Esempio di Utilizzo

```bash
> Aggiungi una domanda sulle differenze tra HTTP/1.1 e HTTP/2
> Modifica la domanda 3 con "Spiega il pattern MVC"
> Elimina la domanda numero 5
> Mostra tutte le domande
> exit
```

### Struttura del Progetto

```bash
.
├── src
│   ├── main
│   │   ├── java/io/cognitiveforge
│   │   │   ├── config      # Configurazioni Spring
│   │   │   ├── service     # Business logic e integrazione LLM
│   │   │   ├── model       # DTO e classi dominio
│   │   │   └── util       # Utilities e parser
│   │   └── resources       # File di configurazione
│   └── test                # Test unitari e integrazione
├── questions.txt           # Database delle domande
└── pom.xml                 # Configurazione Maven
```

## Licenza

Distribuito sotto licenza MIT. Vedi LICENSE per maggiori dettagli.



