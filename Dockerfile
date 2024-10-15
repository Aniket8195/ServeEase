FROM openjdk:19-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# Ensure the mvnw script is executable
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port
EXPOSE 8080

# Run the jar from target folder after build
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "target/ServeEase.jar"]
