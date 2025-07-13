# Use a lightweight Java 17 runtime environment
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the pre-built app.jar from the build context into the container
COPY app.jar .

# Expose the port the Spring Boot application runs on
EXPOSE 8080

# The command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]