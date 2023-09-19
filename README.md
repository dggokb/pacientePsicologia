#Para utilizar o Mongo no docker, rodar o comando abaixo:
docker run --name mongodb -d -p 27017:27017 mongo

#Para executar a aplicação, na pasta principal do projeto, rodar o comando abaixo:
mvn spring-boot:run -U -DskipTests
