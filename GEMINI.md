# Project Overview

This is a Java Spring Boot project that appears to be a clone of Instagram. It uses Java 21, Gradle, and Spring Boot 3. The project follows a classic Model-View-Controller (MVC) architecture.

## Key Technologies

*   **Backend:** Spring Boot, Spring Security, Spring Data JPA
*   **Frontend:** Thymeleaf
*   **Database:** MySQL
*   **Build Tool:** Gradle
*   **Language:** Java 21

## Architecture

*   **Controllers:** Handle web requests and orchestrate responses.
*   **Services:** Contain the business logic of the application.
*   **Repositories:**  Used to access data from the database.
*   **Entities:** Represent the data model of the application.
*   **DTOs:** Data Transfer Objects are used to transfer data between the layers of the application.

# Building and Running

## Prerequisites

*   Java 21
*   MySQL database

## Configuration

1.  **Database:** Create a MySQL database named `instagram`.
2.  **`application.properties`:** Update the `spring.datasource.username` and `spring.datasource.password` in `src/main/resources/application.properties` with your MySQL credentials.

## Running the Application

You can run the application using the following Gradle command:

```bash
./gradlew bootRun
```

# Development Conventions

*   **Lombok:** The project uses Lombok to reduce boilerplate code.
*   **DTOs:** DTOs are used to separate the internal data model from the data exposed to the client.
*   **Service Layer:** Business logic is encapsulated in a service layer.
*   **Error Handling:** The project has a custom exception handling mechanism.
*   **Security:** Spring Security is used to handle authentication and authorization.
