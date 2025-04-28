# Dockerfile for discovery-server
FROM maven:3.8.5-openjdk-17 AS builder

# Copy entire project structure
WORKDIR /workspace
COPY .. .

# Install parent POM first (from root directory)
WORKDIR /workspace
RUN mvn install -N

# First build shared-library
WORKDIR /workspace/shared-library
RUN mvn clean install -DskipTests

# Build discovery-server
WORKDIR /workspace/notification-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/notification-service/target/*.jar app.jar

EXPOSE 3003
ENTRYPOINT ["java", "-jar", "app.jar"]