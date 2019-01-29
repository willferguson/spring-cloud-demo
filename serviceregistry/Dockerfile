FROM openjdk:8-alpine

RUN mkdir /app
COPY target/${project.artifactId}-${project.version}.jar /app/app.jar
CMD java -jar /app/app.jar
