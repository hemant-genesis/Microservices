# FROM maven:3.9.4-eclipse-temurin-17 AS build
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src ./src
# RUN mvn clean package -DskipTests
# FROM eclipse-temurin:17-jdk-alpine
# COPY --from=build /app/target/product-service-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 2000
# ENTRYPOINT ["java", "-jar", "app.jar"]


# Step 1: Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim as builder

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Maven wrapper and pom.xml (so we can build the project)
COPY ./mvnw ./mvnw
COPY ./pom.xml ./pom.xml

# Step 4: Download the project dependencies (just once to cache them)
RUN ./mvnw dependency:go-offline

# Step 5: Copy the full project into the container
COPY ./src ./src

# Step 6: Build the application
RUN ./mvnw clean package -DskipTests

# Step 7: Now, create the final image with only the JAR file
FROM openjdk:17-jdk-slim

# Set the location of the JAR file
COPY --from=builder /app/target/*.jar /app/product-service.jar

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]

# Expose the application port
EXPOSE 2000