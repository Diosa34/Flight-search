# Flight Search
```text
Группа: P33091
Вариант: 1907
```
## Структура
```text
- config
- controllers
- exceptions
- models
- repositories
- services
- schemas
```
- config – Классы конфигурации
- controllers – Конроллеры
- models – Сущности (Модели)
- repositories – Репозитории
- services – Сервисы, включая сервисы которые управляют БД
- schemas – Обьект передаваемых данных (Transfer Data Object), 
обекты отвечающие за валидацию данных. 
Пользовательские данные передаются в tdo, после преобразуются в сущность (модель).
Преобразование происходит посредством сервиса, управляющего БД.
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
