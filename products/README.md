# Microservice - Products

## Project Description
Colaborative Proyect for Ascend program, is an Springboot aplication with maven and using MongoDB for save data.  

## Integrate with your tools
For run application we need 
- Java 11
- Docker
- Docker Compose


## Getting started
For run all proyect we can use docker compose
> `Docker compose up`

or run only database with Docker and run java with your IDE
> `Docker compose -f docker-compose-dev.yaml up`

## Usage
All Proyec will run in
>- 1- MongoDB is in por 27017
- 2- Mongo Express is in port 8081
- 3- Proyect is in port 8100
- 4- Eurecka is in port 8761
- 5- Zipking is in port 9411
- 6 Sonarqube is in port 9000

## Documentation
- There is a openapi.yaml in the root folder that provides the documentation for this service, we can see it in <https://editor.swagger.io/>

- In Globallogic.postman_collection file you can find the endpoints we need open it in Postman

## Support and Authors
>Cusi David

## License
>Ascend Proyect.