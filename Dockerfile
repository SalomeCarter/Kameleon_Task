FROM openjdk:17-jdk-slim
COPY target/*.jar /Kameleon-Task.jar
ENTRYPOINT ["java", "-jar", "/Kameleon-Task.jar"]
