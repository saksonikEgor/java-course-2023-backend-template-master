FROM openjdk:21

WORKDIR /app

COPY target/bot.jar app.jar

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]
