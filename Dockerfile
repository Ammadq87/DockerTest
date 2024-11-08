FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew && ./gradlew build --no-daemon

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/core-0.0.1-SNAPSHOT.jar"]