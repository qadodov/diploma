# Отчет о проведенном тестировании

## Краткое описание

Проведено тестирование веб-сервиса по покупке тура и его взаимодействия с СУБД. Все тестовые сценарии автоматизированы. 

Проверена возможность покупки двумя разными способами:
+ Покупка тура по дебетовой карте
+ Покупка тура в кредит 

Проверена возможность отправки приложением данных банковским сервисам:
+ сервису платежей (Payment Gate)
+ кредитному сервису (Credit Gate)

Проверено взаимодействие с двумя СУБД:
+ MySQL
+ PostgreSQL

Протестирована возможность записи и сохранения данных в БД о способе оплаты, а также об успешности оплаты.

## Количество тест-кейсов

Всего 20 тест кейсов. Из них 12 успешных (60%) и 8 не успешных (40%).

## Отчеты по результатам прогона тестов

Отчёт сгенерированный [Gradle](https://github.com/qadodov/diploma/issues/2) \
Отчёт сгенерированный [Allure](https://github.com/qadodov/diploma/issues/3)

## Рекомендации

Предложения по доработке и найденные ошибки изложены в разделе [Issues](https://github.com/qadodov/diploma/issues)
