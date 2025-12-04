# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]