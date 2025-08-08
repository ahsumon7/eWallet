# -------- Build Stage --------
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make mvnw executable and build the app
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# -------- Run Stage --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=builder /app/target/bank-service-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
