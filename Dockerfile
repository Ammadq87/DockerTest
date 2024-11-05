# Use an official OpenJDK 21 image
FROM openjdk:21-jdk-slim

LABEL authors="ammadqureshi"

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Grant execution permissions to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application (optional step, could be done outside Docker if pre-built)
RUN ./gradlew build --no-daemon

# Expose the port the app runs on
EXPOSE 8080

# Use entrypoint to run the application
ENTRYPOINT ["./gradlew", "bootRun"]
