# --------------------- BUILD STAGE ---------------------
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /build

# Copy only the parent POM
COPY pom.xml .

# Install parent POM (install dans le repo local ~/.m2)
RUN mvn install -N

# Copy discovery-server sources and its pom
COPY discovery-server ./discovery-server

# Build discovery-server
WORKDIR /build/discovery-server
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
COPY --from=builder /build/discovery-server/target/*.jar app.jar

ENV SERVER_PORT=8761
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]