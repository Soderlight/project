FROM openjdk:8-jdk-alpine
ADD out/artifacts/project_jar/project.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project.jar"]