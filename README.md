# E-Commerce REST API & Relational Database System

A full-featured backend system for an online retail shop (`Online-Handel`) built with **Java 17**, **Spring Boot**, **JDBC**, and **PostgreSQL**.

Rather than relying on an ORM like JPA/Hibernate, this project implements data persistence directly via **JDBC** and enforces complex business rules, input validation, and real-time inventory management (`Lagerbestand`) directly at the database layer using **PostgreSQL Triggers, Check Constraints, Regular Expressions, and Views**.

## Tech Stack
* **Backend:** Java 17, Spring Boot, JDBC (MVC architecture with `controller`, `model`, and `repository` layers)
* **Database:** PostgreSQL (containerized via Docker Compose)
* **Build & Documentation:** Maven, Swagger UI

## Project Structure
```text
├── docker-compose.yml
├── docker-compose-persistent.yml
└── spring-boot
    ├── pom.xml
    └── src/main
        ├── java/com/dbw/spring_boot
        │   ├── Application.java
        │   ├── controller/   # REST endpoints (CRUD, Login, Reports)
        │   ├── model/        # Entities & DTOs (KundeAdresseDTO, etc.)
        │   └── repository/   # Raw JDBC data access layer
        └── resources
            ├── application.properties
            ├── schema.sql    # DDL, Regex checks, Triggers & SQL Views
            └── data.sql      # Seed data
