# Brise
A modular, self-sufficient AI orchestrator for local execution. Built entirely in Kotlin with a Compose Multiplatform UI, handling RAG pipelines and LLM management via Ktor.

<div align="center">

![Kotlin](https://img.shields.io/badge/Kotlin-2.4-7F52FF?style=flat-square&logo=kotlin)
![Compose](https://img.shields.io/badge/Compose-Multiplatform-009688?style=flat-square)
![Ktor](https://img.shields.io/badge/Ktor-Backend-FF6F00?style=flat-square&logo=ktor)
![Status](https://img.shields.io/badge/status-active-brightgreen?style=flat-square)

</div>

## Status: Architectural Planning & Core Development
*This project is currently in the active design and initial implementation phase. The documentation below outlines the target architecture and data flow.*

## Architecture Design

Brise is designed with strict separation of concerns, ensuring the UI remains lightweight while the core handles heavy AI orchestration tasks. The architecture is divided into three main layers:

**1. The Core (Ktor Server):** The brain of the application. Its main purpose is to provide the interface for communication with the LLM provider such as Ollama via Ktor. It is strictly decoupled by design, allowing to easily add support for new AI providers in the future without changing the underlying business logic.

**2. The RAG Pipeline:** A dedicated module responsible for local knowledge management. It handles document ingestion, vectorization, and semantic search, ensuring that prompts are augmented with relevant context before reaching the Core.

**3. The UI (Compose Multiplatform):** A reactive, declarative user interface that simply observes states from the backend. Built to run efficiently on Desktop environments, providing a native experience without the heavy resource overhead of Electron-based wrappers.

## How It Works: The RAG Pipeline

When a user interacts with Brise, the will system follow a clear, localized execution path:

1.  **Ingestion:** The user provides local documents. The Core processes and vectorizes this data, storing it in a lightweight local vector structure.
2.  **Query Processing:** A user prompt is sent through the UI. The Core intercepts this, performs a semantic search against the local vectors, and retrieves the most relevant context.
3.  **Context Injection:** The original prompt is augmented with the retrieved context to form a highly specific instruction.
4.  **LLM Execution:** The Ktor client sends the augmented prompt to the local Ollama instance.
5.  **Streaming Response:** As Ollama generates the response token by token, Ktor streams this data back to the Core, which updates the Compose UI reactively.

## Project Goals
- Provide a zero-cost, privacy-first alternative to cloud-based AI orchestrators.
- Deliver a frictionless, out-of-the-box desktop application for local AI orchestration
- Enable a dynamic ecosystem of modular RAGs, allowing users to share knowledge bases across different LLMs and seamlessly swap them mid-conversation.
