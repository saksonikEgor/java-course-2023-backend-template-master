![Bot](https://github.com/saksonikEgor/java-course-2023-backend-template-master/actions/workflows/bot.yml/badge.svg)
![Scrapper](https://github.com/saksonikEgor/java-course-2023-backend-template-master/actions/workflows/scrapper.yml/badge.svg)

# Link Tracker

ФИО: Саксоник Егор Дмитриевич

Приложение для отслеживания обновлений контента по ссылкам.
При появлении новых событий отправляется уведомление в Telegram.

Проект состоит из 2-х приложений:
* Bot
* Scrapper

## Дизайн приложения
<img width="663" alt="Снимок экрана 2024-04-16 в 12 20 50" src="https://github.com/saksonikEgor/java-course-2023-backend-template-master/assets/105850629/58125b11-f313-432e-a667-99c3f91b2539">

## Технологический стэк
* Java 21
* Spring Boot 3
* JPA, JdbcTemplate, JOOQ (кофнигурируемый вобор технологии для работы с бд)
* Liquibase
* PostgreSQL
* Kafka
* [Pengrad Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api)
* Docker
* JUnit 5, Testcontainers, Mockito, Wiremock
* Prometheus, Grafana

## Swagger документация к API
[Bot API](https://github.com/saksonikEgor/java-course-2023-backend-template-master/blob/master/bot/src/main/resources/bot-api.json)  
[Scrapper API](https://github.com/saksonikEgor/java-course-2023-backend-template-master/blob/master/scrapper/src/main/resources/scrapper-api.json)
