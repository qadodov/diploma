# Дипломный проект по курсу "Тестировщик ПО"

В рамках дипломной работы проведена автоматизация тестирования приложения для покупки тура, взаимодействующего с СУБД и банковскими сервисами.

## Инструкция по запуску
#### Для запуска приложения необходимо установить следующее ПО:

* Git
* Java
* Docker Desktop
* Google Chrome

#### Шаги:
1. Запустить Docker Desktop
2. Запустить контейнеры БД и банковскими сервисами в фоновом режиме с помощью введа в терминал команды 
`docker-compose up`
3. Запустить SUT с помощью ввода в терминал команды: 
  + Для запуска с поддержкой MySQL: `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/test -jar aqa-shop.jar`
  + Для запуска с поддержкой PostgreSQL: `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/test -jar aqa-shop.jar`
4. Открыть новое окно терминала и запустить в нём автотесты:
+ Для запуска с поддержкой MySQL - `./gradlew test -Dtest_db_url=jdbc:mysql://localhost:3306/test`
+ Для запуска с поддержкой PostgreSQL - `./gradlew test -Dtest_db_url=jdbc:postgresql://localhost:5432/test`

Для остановки приложения в терминале с запущенной SUT нажать комбинацию клавиш CTRL+C \
Для удаления запущенных Docker-контейнеров ввести в терминал команду `docker-compose down`
