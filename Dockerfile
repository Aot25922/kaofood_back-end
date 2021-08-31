FROM maven:3.8.2-jdk-11-slim as build
WORKDIR /app
COPY pom.xml .
COPY /src ./src
RUN mvn package

FROM openjdk:11.0.12-jre-slim
ARG JAR_File=/app/target/*.jar
COPY --from=build ${JAR_File} app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar 
