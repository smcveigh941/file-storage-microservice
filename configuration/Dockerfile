FROM openjdk:11-jre-slim

RUN mkdir -p /usr/src/file-storage-microservice
WORKDIR /usr/src/file-storage-microservice

COPY service/target/*.jar /usr/src/file-storage-microservice/app.jar
ENTRYPOINT ["java","-jar","/usr/src/file-storage-microservice/app.jar"]