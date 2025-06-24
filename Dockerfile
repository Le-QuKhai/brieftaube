# Stage 1: Build the application using Gradle
FROM gradle:8.10.2-jdk17 AS builder
WORKDIR /app


COPY . .

# --- START DEBUGGING STEPS ---
# Debugging: List files in /app to ensure everything is copied
RUN echo "--- Listing files in /app ---" && ls -l /app

# Debugging: Show content of settings.gradle
RUN echo "--- Content of settings.gradle ---" && cat /app/settings.gradle

# Debugging: Show content of build.gradle (first 20 lines)
RUN echo "--- Content of build.gradle (first 20 lines) ---" && head -n 20 /app/build.gradle

# Debugging: Show current /etc/resolv.conf in container
RUN echo "--- Current /etc/resolv.conf in container (should now reflect daemon.json DNS) ---" && cat /etc/resolv.conf

# Debugging: Test network connectivity to Maven Central and show full curl output
# We're trying to fetch a specific .pom file, which is a common dependency
RUN echo "--- Testing network connectivity to Maven Central ---" \
    && curl -I --max-time 10 https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-gradle-plugin/3.3.4/spring-boot-gradle-plugin-3.3.4.pom \
    >> /tmp/curl_maven_output.log 2>&1 \
    || echo "CURL FAILED for Maven Central - check network/proxy" # This ensures a message even on timeout/error
RUN cat /tmp/curl_maven_output.log # Always show the log content

# Debugging: Test network connectivity to Gradle Plugin Portal and show full curl output
# We're trying to fetch a specific .pom file for the plugin
RUN echo "--- Testing network connectivity to Gradle Plugin Portal ---" \
    && curl -I --max-time 10 https://plugins.gradle.org/m2/org/springframework/boot/org.springframework.boot.gradle.plugin/3.3.4/org.springframework.boot.gradle.plugin-3.3.4.pom \
    >> /tmp/curl_gradle_output.log 2>&1 \
    || echo "CURL FAILED for Gradle Plugin Portal - check network/proxy" # This ensures a message even on timeout/error
RUN cat /tmp/curl_gradle_output.log # Always show the log content
# --- END DEBUGGING STEPS ---

#RUN gradle clean assemble --no-daemon -x test
RUN gradle clean assemble --no-daemon

# Stage 2: Run the app using a lightweight JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]