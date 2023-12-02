FROM openjdk:8-alpine

# Set the working directory inside the container
WORKDIR /app

# Install curl
RUN apk --no-cache add curl

# Copy the Gradle wrapper files into the container
COPY gradlew .
COPY gradle/ ./gradle/

# Copy the build configuration files
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the source code into the container
COPY src/ .

# Run the Gradle build to download dependencies and build the application
RUN ./gradlew build

# Expose the port that your application will run on
EXPOSE 8080

# Command to run the application
CMD ["./gradlew", "run"]

HEALTHCHECK --interval=30s --retries=5 CMD curl -f http://localhost:8080/static/user-registration.html

