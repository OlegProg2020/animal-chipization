FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} webapi.jar
ENTRYPOINT ["java","-jar","/webapi.jar"]