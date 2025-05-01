# FROM maven:3.9.4-eclipse-temurin-17 AS build
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src ./src
# RUN mvn clean package -DskipTests
# FROM eclipse-temurin:17-jdk-alpine
# COPY --from=build /app/target/discovery-server-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8761
# ENTRYPOINT ["java", "-jar", "app.jar"]

# Step 1: Use a Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the pom.xml and the source code into the container
COPY pom.xml .
COPY src ./src

# Step 4: Build the application and package it as a JAR
RUN mvn clean package -DskipTests

# Step 5: Use a smaller image with JDK to run the application
FROM openjdk:17-jdk-alpine

# Step 6: Set the working directory inside the container
WORKDIR /app

# Step 7: Copy the JAR file from the build image
COPY --from=build /app/target/*.jar app.jar

# Step 8: Expose the port the application will run on (default is 8080 for Spring Boot)
EXPOSE 8080

# Step 9: Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
