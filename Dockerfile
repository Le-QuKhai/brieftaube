FROM gradle:8.10.2-jdk17 AS builder
WORKDIR /app
COPY . .

#RUN gradle clean assemble --no-daemon -x test

RUN gradle clean assemble --no-daemon

RUN echo "--- Listing contents of /app/build/libs/ after assemble ---" \
    && ls -l /app/build/libs/ || echo "/app/build/libs/ not found or empty"

# Stage 2: Run the app using a lightweight JRE

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]