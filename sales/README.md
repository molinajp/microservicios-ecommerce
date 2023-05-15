# Microservice - Orders


## Project Description

This project recovers the sales information for an organization. In port 8200 you can see the endpoints to make a sale, and generate reports on them. To run succesfully this service you will need to execute a docker-compose file provided in the project root directory.

To execute this file:

```
cd <path_to_project>
docker compose up
```

## Integrate with your tools

- You will need Java 11, Docker, Docker Compose to be able to run it.

## Test and Deploy

In orders.postman_collection file you can find the endpoints to create orders, get items or get sales by client id.

## Documentation

There's a openapi.yaml in the root folder that provides the documentation for this service.

