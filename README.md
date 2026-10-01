# Spring Boot Demo Project

This project is a small Spring Boot-style application for learning OOP concepts and REST API design in Java.

## Project structure

- Controller layer: `src/main/java/com/example/springdemo/controller`
- Service layer: `src/main/java/com/example/springdemo/service`
- Repository layer: `src/main/java/com/example/springdemo/repository`
- Model classes: `src/main/java/com/example/springdemo/model`
- Main app: `src/main/java/com/example/springdemo/SpringDemoApplication.java`

## Run the application

```bash
cd /workspaces/PL-Standings-2020-25
mvn spring-boot:run
```

The app runs on:

```text
http://localhost:8080
```

## Available REST endpoints

### Products

- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

### Customers

- `GET /api/customers`
- `GET /api/customers/{id}`
- `POST /api/customers`
- `PUT /api/customers/{id}`
- `DELETE /api/customers/{id}`

## Example request

```bash
curl http://localhost:8080/api/products
```

## Notes

This app demonstrates a real Spring Boot architecture using:

- `@RestController` for APIs
- `@Service` for business logic
- `JpaRepository` for persistence
- JPA entities for database models
- H2 in-memory database for local testing
