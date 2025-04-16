
FROM amazoncorretto:21

LABEL version="1.0"

EXPOSE 8080:8081

WORKDIR  /app

COPY target/movie-catelog-service-0.0.1-SNAPSHOT.jar /app/movie-catelog-service.jar

ENTRYPOINT ["java","-jar","movie-catelog-service.jar"]
