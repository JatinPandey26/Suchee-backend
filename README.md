# Suchee

> ✨ Plan smarter, finish better — Suchee is your intelligent task planner that helps break down goals into manageable steps and encourages completion with smart, fun nudges.

---

## 🚀 Project Overview

Suchee is a full-stack web application designed to help users plan tasks, define steps, and stay motivated using AI-driven suggestions and behavioral nudges.

### 🔧 Tech Stack

| Layer       | Tech                                       |
|-------------|--------------------------------------------|
| Frontend    | React, TailwindCSS, Zustand, Redux (planned) |
| Backend     | Spring Boot, Java, REST APIs               |
| Database    | PostgreSQL (dev) / H2 (CI/test)            |
| DevOps      | GitHub Actions, Maven, Docker (planned)    |
| AI Assist   | OpenAI (planned feature)                   |

---

## 🧩 Features

- ✅ Create, update, and organize tasks
- ✅ Add and reorder steps for each task
- 🧠 (Planned) Generate steps and time estimates using AI
- 🎉 (Planned) Inject fun/completion nudges to boost motivation
- 🔐 User login & session handling (upcoming)
- 📊 Analytics dashboard (upcoming)

---

## 🛠️ Project Structure

### Backend (`/suchee-backend`)

- `src/main/java/...` — Spring Boot services, controllers, models
- `application.yml` — environment-specific config
- REST APIs to:
  - Create and manage tasks/steps
  - Sync frontend state with backend storage

### Frontend (`/suchee-frontend`)

- React + Zustand-based state management
- Modular components for steps, timeline, nudges
- Responsive grid UI for drag-and-drop interactions (in progress)

---

## 🧪 Running the Project Locally

### Prerequisites

- Java 17+
- Node.js 18+
- PostgreSQL (if not using H2 for dev)
- Maven

### Backend

```bash
cd suchee-backend
mvn clean install
java -jar target/suchee.jar
