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
- models – Сущьности (Модели)
- repositories – Репозитории
- services – Сервисы, включая сервисы которые управляют БД
- schemas – Обьект передаваемых данных (Transfer Data Object), 
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
## Сборка и запуск Helios
1. C помощью scp загрузить папку с проектом
```shell
scp -r . helios:~/FlightSearch
```
2. Подключится к helios:
```shell
ssh helios
```
3. Записать пароль от бд в `~/.profile` на helios
```shell
touch ~/.profile
echo "export DB_PWD=пароль" >> ~/.profile
```
4. Переподключиться к helios
```shell
exit
ssh helios
```
5. Переходим в папку проекта. Даем все права оболочке Maven. Запускаем оболочку Maven чтобы он установил что надо.
```shell
cd FlightSearch
chmod 777 mvnw
./mvnw clean install
```
6. Запускаем сервак
```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=helios
```
## Документация
Проект имеет автодокументацию REST API по [адрессу](http://localhost:8080/docs):
```text
http://localhost:8080/docs
```
