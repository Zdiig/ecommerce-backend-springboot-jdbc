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
```

## Key Architecture & Features

### 1. Relational Database & Automated Inventory Triggers (`schema.sql`)
* **Normalized Schema:** 7-table relational model covering customers (`kunde`), addresses (`adresse`, `kunde_hat_adressen`), employees (`mitarbeiter`), products (`produkt`), orders (`bestellung`), and order items (`bestellposition`).
* **Regex & Domain Integrity:** Strict SQL `CHECK` constraints and Regular Expressions validating postal codes (`plz`), street names (`strasse`), house numbers (`hausnummer`), unique email formats, order statuses, and password complexity rules.
* **Automated Stock Management (PL/pgSQL Triggers):** Database triggers automatically verify and deduct product stock (`lagerbestand`) when a `bestellposition` is inserted or updated, block negative stock levels, and automatically restore reserved quantities to `lagerbestand` if an item is deleted or an order's `status` changes to `"storniert"`.

### 2. RESTful CRUD API & Authentication
* **Core Resource Endpoints:** Full CRUD operations (`GET`, `POST`, `PUT`/`PATCH`, `DELETE`) for `/mitarbeiter`, `/kunden`, `/adressen`, `/produkte`, `/bestellpositionen`, and `/bestellungen`.
* **Dynamic Price Calculation:** Order positions (`/bestellpositionen`) automatically calculate `gesamtpreis` based on product price and ordered quantity (`menge`).
* **Authentication Endpoints:** Dedicated `POST` login routes for employees (`/login/mitarbeiter`) and customers (`/login/kunde`) with credential verification and HTTP `200 OK` / `401 Unauthorized` responses.

### 3. Controlling & Analytics Reporting (`/report`)
Dedicated SQL Views exposed via REST endpoints in `ReportController`:
* `GET /report/kunde/summe-anzahl-bestellungen` (`v_kunde_summe_anzahl_bestellungen`): Total order count and cumulative spending (`gesamtsumme`) per customer.
* `GET /report/produkt/verkaufszahlen` (`v_produkt_verkaufszahlen`): Total units sold (`gesamtVerkaufteMenge`), revenue (`umsatz`), and order frequency per product (`sku`).
* `GET /report/mitarbeiter/uebersicht` (`v_mitarbeiter_uebersicht`): Employee activity tracking across managed orders and created products.
* `GET /report/mitarbeiter/bestellstatus-uebersicht` (`v_mitarbeiter_bestellstatus_uebersicht`): Cross-tabulated breakdown of order counts per employee across every order status (`abgeschlossen`, `bezahlt`, `neu`, `storniert`, `versendet`).

## How to Run Locally

### 1. Start the PostgreSQL Database
```bash
docker compose up -d
```

### 2. Build and Run the Spring Boot Server
```bash
cd spring-boot/
mvn clean install
mvn spring-boot:run
```

### 3. Explore the API
* **Base API URL:** `http://localhost:8080`
* **Swagger UI (Interactive API Docs):** `http://localhost:8080/swagger-ui/index.html`
