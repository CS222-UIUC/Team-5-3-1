FROM openjdk:21-jdk-slim

WORKDIR /app

COPY fivethreeone/mvnw .
COPY fivethreeone/.mvn ./.mvn

COPY fivethreeone/target/fivethreeone-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]

# docker stop 3e56942ba023


# ./mvnw clean package -DskipTests
# docker build -t backend-springboot .
# docker run -d -p 8081:8080 backend-springboot