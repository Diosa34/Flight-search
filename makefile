build:
	mvn clean package

run:
	java -jar target/FlightSearch-1.0.0.jar

mvn_i:
	./mvnw clean install

runw:
	./mvnw spring-boot:run -Dspring-boot.run.profiles=helios
