# currency_alfa

## Задание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:


• если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich

• если ниже - отсюда https://giphy.com/search/broke

### Ссылки

• REST API курсов валют - https://docs.openexchangerates.org/

• REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide

Must Have

• Сервис на Spring Boot 2 + Java / Kotlin

• Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD

• Для взаимодействия с внешними сервисами используется Feign

• Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки

• На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)

• Для сборки должен использоваться Gradle

• Результатом выполнения должен быть репо на GitHub с инструкцией по запуску

Nice to Have

• Сборка и запуск Docker контейнера с этим сервисом

## Endpoint
GET "/api/currency/gif/{code}"

HttpStatus.NOT_FOUND если введен неверный код валюты

HttpStatus.NOT_FOUND если все в порядяке

## Запуск
### jar
Из корневой папки проекта:

java -jar currency-0.0.1-SNAPSHOT.jar

### Docker
Из корневой папки проекта:

docker build -t dockerappimage .

docker run -p 8080:8080 dockerappimage


