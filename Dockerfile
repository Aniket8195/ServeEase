FROM openjdk:19-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and the .mvn directory to the container
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

# Expose the port on which the Spring Boot application runs
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/ServeEase.jar"]
