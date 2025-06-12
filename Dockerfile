# Stage 1: Build the JAR using Gradle
FROM gradle:8.10.2-jdk17 AS builder

WORKDIR /app
COPY . .

# --- DEBUGGING STEPS (keep for now, remove once working) ---
RUN echo "--- Listing files in /app ---" && ls -l /app
RUN echo "--- Content of settings.gradle ---" && cat /app/settings.gradle
RUN echo "--- Content of build.gradle (first 20 lines) ---" && head -n 20 /app/build.gradle
# !!! IMPORTANT: CORRECTED PATH HERE !!!
RUN echo "--- Current /etc/resolv.conf in container (should now reflect daemon.json DNS) ---" && cat /etc/resolv.conf
RUN echo "--- Testing network connectivity to Maven Central ---" && \
    curl -I --max-time 10 https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-gradle-plugin/3.3.4/spring-boot-gradle-plugin-3.3.4.pom || echo "CURL FAILED for Maven Central - check network/proxy"
RUN echo "--- Testing network connectivity to Gradle Plugin Portal ---" && \
    curl -I --max-time 10 https://plugins.gradle.org/m2/org/springframework/boot/org.springframework.boot.gradle.plugin/3.3.4/org.springframework.boot.gradle.plugin-3.3.4.pom || echo "CURL FAILED for Gradle Plugin Portal - check network/proxy"
# --- END DEBUGGING STEPS ---

RUN gradle clean assemble --no-daemon -x test

# Stage 2: Run the app using a lightweight JRE
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]