# Microservice Ticket Shop

## Overview
This project is a basic microservice architecture implementation for a ticket shop system. It includes the following components:
- **Service Registry**: Manages service discovery for microservices.
- **API Gateway**: Acts as an entry point for client requests, routing them to appropriate services.
- **User Service**: Handles user-related operations.
- **Ticket Service**: Manages ticket-related operations.
- **Redis**: Used for caching to improve performance.
- **Kafka**: Enables event-driven communication between services.

The system also integrates **Redis** for caching and **Kafka** for event-driven communication.

## Prerequisites
- Docker
- Docker Compose

## How to Run
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run the following command to start all services:
   ```bash
   docker-compose up
   ```
   This will build and start all containers defined in the `docker-compose.yml` file.
4. Run the services: Service Registry, API Gateway, User Service, Ticket Service

## How to Stop
To stop and remove all containers, run:
```bash
docker-compose down
```