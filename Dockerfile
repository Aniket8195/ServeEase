
FROM openjdk:19-jdk-alpine


WORKDIR /app


COPY pom.xml ./
RUN ./mvnw dependency:go-offline


COPY src ./src


RUN ./mvnw clean package -DskipTests


EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","target/ServeEase.jar"]
