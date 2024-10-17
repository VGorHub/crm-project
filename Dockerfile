# Build Stage
FROM openjdk:23-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Run Stage
FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/crm_project-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
