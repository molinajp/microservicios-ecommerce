# Vault



## Project Description

This project uses an image for Harshicorp Vault configured in the docker-compose.yaml file located on the root of the project. Also, in this file is the docker image for the project itself, that executes for the only purpose of inserting the secrets in the vault, so they can be consumed in other microservices. In a production environment this wouldn't be optimal, but for testing purposes and implementing in a local environment it's extremely useful.

