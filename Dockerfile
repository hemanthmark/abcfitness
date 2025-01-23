# Start with the official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and install dependencies (to leverage Docker cache)
COPY pom.xml .

# Download dependencies (will be cached if no changes in pom.xml)
RUN mvn dependency:go-offline

# Copy the project files
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Start a new stage for running the application
FROM openjdk:17-jdk-slim

# Set the working directory for the application
WORKDIR /app

# Copy the jar from the build stage to the current stage
COPY --from=build /app/target/test-0.0.1-SNAPSHOT.jar .

# Expose port 8080 (Spring Boot default port)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "test-0.0.1-SNAPSHOT.jar"]
