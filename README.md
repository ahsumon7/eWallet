# 🏦 Bank & Wallet Microservices

A comprehensive microservices architecture built with Spring Boot that enables seamless money transfers between bank accounts and digital wallets.
🏗️ Key Adaptations for Your Setup:
Java 21 Support:

Updated Dockerfiles to use openjdk:21-jdk-slim
Maven compiler plugin configured for Java 21

Your Database Configuration:

Bank Service: PostgreSQL on port 5432 with database bank
Wallet Service: PostgreSQL on port 5433 with database wallet


✨ Features

### Bank Service
- ✅ Create and manage bank accounts
- ✅ Transfer money from bank to wallet
- ✅ Account balance management
- ✅ Transaction history tracking
- ✅ Pessimistic locking for concurrent operations

### Wallet Service
- ✅ Create and manage digital wallets
- ✅ Credit/Debit wallet operations
- ✅ Transfer money from wallet to bank
- ✅ Transaction history tracking
- ✅ Balance validation and management

## 🐳 Docker Setup
Clone the repository and navigate to the project folder:

Start all services with Docker Compose:
docker-compose up --build -d

📚 API Documentation
Bank Service:
Swagger UI: http://localhost:8081/swagger-ui.html

Wallet Service:
Swagger UI: http://localhost:8082/swagger-ui.html









