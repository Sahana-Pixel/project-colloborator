# Use official OpenJDK 17 image
FROM eclipse-temurin:21-jdk-focal


# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom files first
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src ./src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose the port (Render uses $PORT)
EXPOSE 10000

# Run the JAR
ENTRYPOINT ["java","-jar","target/springboot-check-0.0.1-SNAPSHOT.jar"]
