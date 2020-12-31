FROM openjdk:11-jre-slim

RUN adduser --system --group --home /opt/app appuser

ARG JAR_FILE=service/target/*.jar
COPY --chown=appuser:appuser ${JAR_FILE} /opt/app/application.jar

USER appuser
WORKDIR /opt/app
ENTRYPOINT ["java", "-jar", "/opt/app/application.jar"]