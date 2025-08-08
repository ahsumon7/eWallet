
🏦 eWallet 

A comprehensive microservices architecture built with Spring Boot that enables seamless money transfers between bank accounts and digital wallets.

## 🛠️ Technologies

- **Java 21**
- **Spring Boot 3.2**
- **PostgreSQL** (Dockerized)
- **Spring Data JPA**
- **Spring Web**
- **Lombok**
- **Docker**
- **Swagger UI**


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

Service	        Port	Database  Port    Swagger UI
Bank Service	  8081	bank	    5432    http://localhost:8081/swagger-ui.html     
Wallet Service	8082	wallet	  5433    http://localhost:8082/swagger-ui.html


Bank Service Swagger: http://localhost:8081/swagger-ui.html
Wallet Service Swagger: http://localhost:8082/swagger-ui.html










