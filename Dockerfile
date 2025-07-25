# Stage 1: Build the application
# Usar una imagen base de OpenJDK con Maven (o Gradle si lo usas)
FROM openjdk:17-jdk-slim as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven pom.xml and src directory
# This step is optimized for Docker caching:
# Copy pom.xml first to download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean install -DskipTests

FROM openjdk:24-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/catapiweb-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]