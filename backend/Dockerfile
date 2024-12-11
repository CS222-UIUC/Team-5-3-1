FROM openjdk:21-jdk-slim

WORKDIR /app

COPY fivethreeone/mvnw .
COPY fivethreeone/.mvn ./.mvn

COPY fivethreeone/target/fivethreeone-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]

# set up API as environment variable 

# in fivethree one directiory run: 
# ./mvnw clean package -DskipTests

# in team-5-3-1 directory run:
# docker build -t backend-springboot .
# docker run -d -p 8081:8080 backend-springboot

# when done run: 
# docker stop 3e56942ba023