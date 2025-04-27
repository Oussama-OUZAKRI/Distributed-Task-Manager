FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY shared-library/pom.xml .
COPY shared-library/src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jdk-alpine AS cache
COPY --from=build /root/.m2 /root/.m2