FROM openjdk:21

WORKDIR /app

COPY target/bot.jar app.jar

EXPOSE 8090

CMD ["java", "-jar", "-DTELEGRAM_TOKEN.token=${TELEGRAM_TOKEN}","app.jar"]
