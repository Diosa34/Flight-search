# Документооборот
## Структура
```text
+-- config    
│   \-- properties
+-- controllers
+-- exceptions
│   +-- repositories
│   \-- schemas
+-- models
+-- repositories
+-- schemas
|   \-- jms
+-- security
\-- services
    +-- generators
    \-- mapping
```
- config – Классы конфигурации
- config.properties – Классы свойств
- controllers – Конроллеры
- controllers.jms – Конроллеры обрабатывающие входные jms сообщения
- exceptions – Классы ошибок
- exceptions.repositories – Классы репозиториев
- exceptions.schemas – Схемы (TDO) возвращаемые конторолером ошибок.
- models – Сущности (Модели)
- repositories – Репозитории
- schemas – Объект передаваемых данных (Transfer Data Object),
  объекты отвечающие за валидацию данных.
- schemas.jms – Объект передаваемых данных по jms (Transfer Data Object)
- security – Все относящиеся к безопасности
- services – Сервисы, включая сервисы которые управляют БД.
- services.generators – Сервисы формирующие определенные типы документов, 
  в каждом сервисе определен метод который формирует и сохраняет файл и сохраняет запись в бд о сгенерированном файле.
- services.mapping – Сервисы преобразующие объекты схем в объекты моделей.

## BPMN
Диаграмма документооборота
### Действия выполняемые владельцем документа
![Действия выполняемые владельцем документа](/docs/bpmn/document-holder.png)

### Действия выполняемые подписчиком документа
![Действия выполняемые подписчиком документа](/docs/bpmn/document-signer.png)

## Сборка и запуск
**! Важно:** Для работы приложения необходимо установить ActiveMQ.
Для установки и запуска ActiveMQ на MacOS выполнить:
```shell
brew install apache-activemq
brew services start activemq
```

Конфигурации для запуска в IntelliJ IDEA находятся в [.run](.run).
Для сборки проекта выполнить
```shell
./mvnw package
```
jar файл будет в `/target`. Для запуска сервера, 
предварительно нужно установить переменные среды DB_USER, DB_PWD, DB_URL:
```shell
export DB_URL=jdbc:postgresql://localhost:5432/test
export DB_USER=test
export DB_PWD=test
```
Для запуска микросервиса необходимо выбрать профиль микросервиса в параметре `-Dspring.profiles.active`.
```shell
# Запуск основного микросервиса
java -jar -Dspring.profiles.active=prodMain FlightSearch-1.0.0.jar
# Запуск микросервиса платежных поручений
java -jar -Dspring.profiles.active=prodMain FlightSearch-1.0.0.jar
```

## Микросервисы
### Основной микросервис (REST API)
Профили запуска: `devMain`, `prodMain`.
Задачи: обработка клиентских REST запросов. Создание и загрузка документов, 
подписание документов.
### Микросервис платежных поручений
Профили запуска: `devPayroll`, `prodPayroll`.
Задачи: формирование огромного количества платежных поручений.

## Документация
Проект имеет автодокументацию REST API по [адресу](http://localhost:8080/docs):
```text
http://localhost:8080/docs
```

## Задание и варианты
### Лабораторная работа №1
Вариант: `custom`
#### Порядок выполнения работы:
1. Выбрать один из бизнес-процессов, для системы документооборота.
2. Утвердить выбранный бизнес-процесс у преподавателя.
3. Специфицировать модель реализуемого бизнес-процесса в соответствии с требованиями `BPMN 2.0`.
4. Разработать приложение на базе Spring Boot, реализующее описанный на предыдущем шаге бизнес-процесс. 
Приложение должно использовать `СУБД PostgreSQL` для хранения данных, 
для всех публичных интерфейсов должны быть разработаны `REST API`.
5. Разработать набор curl-скриптов, либо набор запросов для REST клиента Insomnia для тестирования 
публичных интерфейсов разработанного программного модуля. Запросы Insomnia оформить в виде файла экспорта.
6. Развернуть разработанное приложение на сервере helios.

### Лабораторная работа №2
Вариант: `1918`
#### Цель
Доработать приложение из лабораторной работы #1, 
реализовав в нём управление транзакциями и 
разграничение доступа к операциям бизнес-логики в соответствии 
с заданной политикой доступа.
#### Управление транзакциями необходимо реализовать следующим образом:
* Переработать согласованные с преподавателем прецеденты (или по согласованию с ним разработать новые), 
объединив взаимозависимые операции в рамках транзакций.
* Управление транзакциями необходимо реализовать с помощью `Spring JTA`.
* В реализованных (или модифицированных) прецедентах необходимо использовать декларативное управление транзакциями.
* ~~В качестве менеджера транзакций необходимо использовать Bitronix.~~
#### Разграничение доступа к операциям необходимо реализовать следующим образом:

* Разработать, специфицировать и согласовать с преподавателем набор привилегий, 
в соответствии с которыми будет разграничиваться доступ к операциям.
* Специфицировать и согласовать с преподавателем набор ролей, осуществляющих доступ к операциям бизнес-логики приложения.
* Реализовать разработанную модель разграничений доступа к операциям бизнес-логики на базе `Spring Security + JAAS`. 
Информацию об учётных записях пользователей необходимо сохранять в файле XML, для аутентификации использовать `HTTP basic`.

### Лабораторная работа №3
Вариант: `1921`
#### Цель
Доработать приложение из лабораторной работы #2, реализовав в нём асинхронное выполнение задач с 
распределением бизнес-логики между несколькими вычислительными узлами и выполнением периодических 
операций с использованием планировщика задач.

#### Требования к реализации асинхронной обработки:
* Перед выполнением работы необходимо согласовать с преподавателем набор прецедентов, 
в реализации которых целесообразно использование асинхронного распределённого выполнения задач. 
Если таких прецедентов использования в имеющейся бизнес-процесса нет, 
нужно согласовать реализацию новых прецедентов, 
доработав таким образом модель бизнес-процесса из лабораторной работы #1.
* Асинхронное выполнение задач должно использовать модель доставки `очередь сообщений`.
* В качестве провайдера сервиса асинхронного обмена сообщениями необходимо использовать очередь сообщений 
на базе `Apache ActiveMQ`.
* Для отправки сообщений необходимо использовать протокол `STOMP`. 
Библиотеку для реализации отправки сообщений можно взять любую на выбор студента.
* Для получения сообщений необходимо использовать `JMS API`.
* 
#### Требования к реализации распределённой обработки:
* Обработка сообщений должна осуществляться на двух независимых друг от друга узлах сервера приложений.
* Если логика сценария распределённой обработки предполагает транзакционное выполнение операций,
то они должны быть включены в состав распределённой транзакции.

#### Требования к реализации запуска периодических задач по расписанию:
* Согласовать с преподавателем прецедент или прецеденты, 
в рамках которых выглядит целесообразным использовать планировщик задач. 
Если такие прецеденты отсутствуют -- согласовать с преподавателем новые и добавить их в модель автоматизируемого 
бизнес-процесса.
* Реализовать утверждённые прецеденты с использованием планировщика задач `Quartz`.

