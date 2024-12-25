# Stage 1: Build
FROM openjdk:19-jdk-alpine AS build

WORKDIR /app

# Install required packages
RUN apk add --no-cache curl unzip

# Specify Maven version
ENV MAVEN_VERSION=3.9.9

# Download and install Maven
RUN curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.zip" -o /tmp/maven.zip \
    && unzip /tmp/maven.zip -d /opt/ \
    && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven \
    && rm /tmp/maven.zip

# Set environment variables for Maven
ENV MAVEN_HOME=/opt/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# Copy project files and build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:19-jdk-alpine

WORKDIR /app

# Copy only the JAR file from the build stage
COPY --from=build /app/target/ServeEase-0.0.1-SNAPSHOT.jar .

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ServeEase-0.0.1-SNAPSHOT.jar"]
