# --------------------- BUILD STAGE ---------------------
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /build

# Copy only the parent POM
COPY pom.xml .

# Install parent POM (install dans le repo local ~/.m2)
RUN mvn install -N

# Copy shared-library (indispensable pour tous les services)
COPY shared-library ./shared-library
RUN mvn install -f shared-library/pom.xml -DskipTests

# Copy task-service sources and its pom
COPY task-service ./task-service

# Build task-service
WORKDIR /build/task-service
RUN mvn clean package -DskipTests

# --------------------- RUNTIME STAGE ---------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Installer curl
USER root
RUN apk add --no-cache curl

# Créer un utilisateur d'exécution sécurisé
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Only copy the final JAR
COPY --from=builder /build/task-service/target/*.jar app.jar

ENV SERVER_PORT=3000
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]