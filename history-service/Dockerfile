# Dockerfile générique pour les microservices Spring Boot
FROM eclipse-temurin:17-jdk-alpine AS builder

# Copier le cache Maven avec la shared-library
COPY --from=shared-library:latest /root/.m2 /root/.m2

# Étape de build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Étape d'exécution
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copie du JAR depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Optimisations pour Spring Boot
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
EXPOSE 3001

# Commande de démarrage avec paramètres supplémentaires
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]