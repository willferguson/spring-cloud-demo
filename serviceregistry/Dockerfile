FROM openjdk:8u201-jre-alpine3.9
ADD target/serviceregistry-1.0-SNAPSHOT.jar /app/service.jar
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
