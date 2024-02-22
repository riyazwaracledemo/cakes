FROM openjdk:21-slim
EXPOSE 8080
COPY ../target/*.jar /opt/app/cakes-api.jar
ENTRYPOINT ["java", "-showversion", "-jar", "/opt/app/cakes-api.jar"]