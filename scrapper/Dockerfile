FROM openjdk:21

WORKDIR /app

COPY target/scrapper.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
