# Microservice - Clients



## Project Description

This project recovers the clients for an organization. It runs on port 8000 and it has only one endpoint (/v1/clients/{id}), where it will return the user and its details if exists, or an error if it doesn't. For this action it uses a mocked rest service, and it only has responses for ids from 1 to 5.

## Execution

To execute this service there's a docker-compose file provided in src/main/resources/config. To execute this file:

```
cd <path_to_project>
docker compose up
```

**You need to have installed Java 11, Docker and Docker Compose to be able to run it.**

## Documentation

There's a openapi.yaml in the root folder that provides the documentation for this service.
