# Flight Search
```text
Группа: P33091
Вариант: 1907
```
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
+-- security
\-- services
    \-- mapping

```
- config – Классы конфигурации
- config.properties – Классы свойств
- controllers – Конроллеры
- exceptions – Классы ошибок
- exceptions.repositories – Классы репозиториев
- exceptions.schemas – Схемы (TDO) возвращаемые конторолером ошибок.
- models – Сущности (Модели)
- repositories – Репозитории
- schemas – Обьект передаваемых данных (Transfer Data Object),
обекты отвечающие за валидацию данных.
- security – Все относящиеся к безопасности
- services – Сервисы, включая сервисы которые управляют БД.
- services.mapping – Сервисы преобразущие обьекты схем в обекты моделей.
## Сборка и запуск
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

java -jar target/FlightSearch-1.0.0.jar
```
## Сборка и запуск Helios
1. C помощью scp загрузить jar
```shell
scp target/FlightSearch-1.0.0.jar helios:~/
```
2. Подключится к helios:
```shell
ssh helios
```
3. Записать пароль от бд в `~/.profile` на helios. 
По умолчанию сервер запускается на порту 14880, 
его можно переопределить добавив переменную среды PORT.
```shell
touch ~/.profile
echo "export DB_USER=sXXXXXX" >> ~/.profile
echo "export DB_PWD=пароль" >> ~/.profile
```
4. Переподключиться к helios
```shell
exit
ssh helios
```
5. Запускаем сервак. `-Dspring.profiles.active` - определяет профиль конфигурации
```shell
java -jar -Dspring.profiles.active=helios FlightSearch-1.0.0.jar
```
## Документация
Проект имеет автодокументацию REST API по [адрессу](http://localhost:8080/docs):
```text
http://localhost:8080/docs
```
