# Flight Search
```text
Группа: P33091
Вариант: 1907
```
## Структура
```text
- config
- controllers
- models
- repositories
- services
- tdo
```
- config – Классы конфигурации
- controllers – Конроллеры
- models – Сущьности (Модели)
- repositories – Репозитории
- services – Сервисы, включая сервисы которые управляют БД
- tdo – Обьект передаваемых данных (Transfer Data Object), 
обекты отвечающие за валидацию данных. 
Пользовательские данные передаются в tdo, после преобразуются в сущьность (модель).
Преобразование происходит посредством сервиса, управляющего БД.
## Сборка и запуск
Для сборки проекта выполнить
```shell
make buid
```
или
```shell
mvn clean package
```
jar файл будет в `/target`. Для запуска сервера:
```shell
make run
```
или
```shell
java -jar target/FlightSearch-1.0.0.jar
```
## Документация
Проект имеет автодокументацию REST API по [адрессу](http://localhost:8080/docs):
```text
http://localhost:8080/docs
```
