# Dockerfile for discovery-server
FROM maven:3.8.5-openjdk-17 AS builder

# Copy entire project structure
WORKDIR /workspace
COPY .. .

# Install parent POM first (from root directory)
WORKDIR /workspace
RUN mvn install -N

# Build discovery-server
WORKDIR /workspace/discovery-server
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/discovery-server/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
