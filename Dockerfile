# Start with a base image containing Java runtime
FROM openjdk:19-jdk-alpine

# Add Maintainer Info
LABEL maintainer="your.email@example.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file, set as a build argument
ARG JAR_FILE=target/ServeEase.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
