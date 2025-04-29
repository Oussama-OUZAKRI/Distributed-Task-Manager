# --------------------- BUILD STAGE ---------------------
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /build

# Copy only the parent POM
COPY pom.xml .

# Install parent POM (install dans le repo local ~/.m2)
RUN mvn install -N

# Copy auth-service sources and its pom
COPY auth-service ./auth-service

# Build auth-service
WORKDIR /build/auth-service
RUN mvn clean package -DskipTests

# --------------------- RUNTIME STAGE ---------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Créer un utilisateur d'exécution sécurisé
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Only copy the final JAR
COPY --from=builder /build/auth-service/target/*.jar app.jar

ENV SERVER_PORT=3002
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]