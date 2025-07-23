# Flipkart Offers API

This Spring Boot application parses, stores, and evaluates Flipkart-like promotional offers and provides an API to calculate the best applicable discount for a given payment scenario.

---

## 📦 Features

- Parse and store offer data in a normalized PostgreSQL schema
- Supports filtering offers by:
    - Bank name
    - Payment instrument (e.g., Credit Card, UPI, EMI)
    - EMI duration
- Calculates the highest discount applicable for a payment
- Automatically creates database tables using JPA/Hibernate
- Well-structured schema for scaling and extensibility

---

## 🏗️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Gradle (Groovy DSL)**
- **Lombok**


---

## 🗃️ Database Schema Design

1. `offers` — Main offer table
2. `offer_banks` — Maps offers to eligible banks
3. `offer_payment_instruments` — Maps offers to payment instruments
4. `offer_emi_months` — Stores EMI months per offer
5. `offer_card_networks` — Stores card network restrictions
6. `offer_display_tags` — UI tags like “Bank Offer”, “No Cost EMI”, etc.

Each child table is normalized and uses `ON DELETE CASCADE` to maintain integrity with the main `offers` table.

---
## ☁ PostgreSQL Setup
To run this application locally, you'll need a PostgreSQL database running. Follow these steps to set it up:

✅ Option 1: Using Docker (Recommended)
If you have Docker installed:

bash
Copy
Edit
docker run --name flipkart-postgres \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=your_password \
-e POSTGRES_DB=flipkart_offers \
-p 5432:5432 \
-d postgres
🔐 Replace your_password with a secure password and use the same in application.properties.

✅ Option 2: Manual Setup (Using pgAdmin or CLI)
Install PostgreSQL
Download and install from https://www.postgresql.org/download/



Create a Database
You can do this from pgAdmin or CLI:
-------------------
## 🚀 Running the Project

### 1. Prerequisites

- Java 17+
- Gradle
- PostgreSQL

### 2. Setup

Update `application.properties`:

`properties
spring.datasource.url=jdbc:postgresql://localhost:5432/flipkart_offers
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

NOTE: Make sure url of your postgres sql is correctly mapped.
### 3. Run

    ./gradlew bootRun

Or build the JAR:

    ./gradlew clean build
    java -jar build/libs/flipkart-offers-1.0.0.jar


### NOTE

All the Sample GET and POST request are mentioned in sample folder.

Also I have created the separate API for Bonus question by the version v2 so that it does not break the original one.

---------------------------------

## Q2:Assumptions you made to complete the assignment

#### 📌 Assumptions Made

##### 1. Offer Parsing

- Offers are provided in a structured JSON format (e.g., via API or from a static file), not scraped or extracted from unstructured HTML.

- Discount information like "up to ₹500 off" or "10% instant discount" is available in title or summary.

##### 2. Bank and Payment Instrument Matching

- A valid offer must explicitly support both the bankName and paymentInstrument provided in the request.

- Bank and instrument names in the input are assumed to match database entries (e.g., "AXIS", "HDFC", "CREDIT_CARD").

#### 3. Discount Calculation Logic

- Only percentage or flat discounts are considered for calculating the best offer.

- "Up to" limits in offers (e.g., “10% up to ₹500”) are strictly enforced as max cap.

- EMI offers like “No Cost EMI” are considered eligible if amountToPay ≥ required cart value and emi_months exist.

#### 4. Database Design

- A normalized schema is preferred to handle one-to-many relations like multiple banks, card networks, or EMI months per offer.

- Cascading deletes are used to keep child tables in sync with offers.

#### 5. API Behavior

- If no valid offers are found, the discount is considered 0.

- The response always returns a valid JSON response (highestDiscountAmount: 0 if none).

#### 6. Case Sensitivity

- Matching of bank and instrument names is case-insensitive during querying.

#### 7. Concurrency & Load

- Initial implementation assumes moderate traffic; no caching or async processing added until scaling requirements arise.

---------------------------------

## Q3:A brief explanation of your design choices (e.g., why you chose a specific framework,database schema decisions).

#### 📌 

#### ✅ Spring Boot (Java)

- Spring Boot is a mature, production-ready framework that simplifies building REST APIs with minimal configuration.

- Benefits:

  - Built-in support for JPA/Hibernate for ORM

  - RESTful endpoints via annotations (@RestController, @GetMapping, etc.)

  - Easy integration with databases, services, and dependency injection

#### ✅ Database: PostgreSQL
- PostgreSQL is a powerful open-source relational database that provides:

- Robust querying capabilities

- Native support for JSON (if needed for offer parsing)

- Strong ACID compliance and indexing features

#### ✅ Schema: Design

- Offers from platforms like Flipkart can vary by:

  - Bank (AXIS, HDFC, etc.)
  - Payment instrument (CREDIT_CARD, EMI, UPI, etc.)
  - Discount type (flat, percentage, up to)
  - Minimum cart value
  - EMI duration (e.g., "3 or 6 months")

- Each field in the schema directly maps to a filtering or calculation need.
Fields like bankName and paymentInstrument are indexed-friendly.
This ensures fast lookups even when you have thousands of offers. 
- Normalized & Clean One table for offers: avoids duplication or unnecessary joins
- Keeps the DB efficient and easy to query/maintain
- Normalized instead of comma-separated lists in a single field.
- Fast querying with indexed access


----------------------------

## Q4:A short note on how you would scale the GET /highest-discount endpoint to handle 1,000 requests per second.

#### 📌 Following are the startegies i will consider:

#### 1. Use Caching

- Layer: Redis or in-memory cache (e.g., Caffeine)

- Strategy: Cache best offer results for common combinations of (bankName, paymentInstrument, amount range)

- Reduces database load for frequent queries (e.g., “AXIS + CREDIT_CARD”)

#### 2. Database Optimization
- Indexes on:
  - offer_banks.bank
  - offer_payment_instruments.instrument
  - offers.adjustment_type, adjustment_sub_type
  - Materialized Views or precomputed discount tables if logic is complex.

#### 3. Horizontal Scaling
- Stateless API → Deploy multiple instances of the service behind a load balancer (e.g., AWS ALB, NGINX).

- Use containerization (Docker + Kubernetes) to auto-scale based on traffic.

#### 4. Rate Limiting & Throttling
- Use a gateway (e.g., API Gateway, or Spring Cloud Gateway) to
limit abusive clients

- Apply burst protection

#### 5. Asynchronous Processing (if applicable)
- Use Spring WebFlux or async controller if offer lookup doesn’t need to block the request thread.
Non-blocking I/O improves throughput under high load.

----------------
## Q5:A short note on what you will improve if you had more time to complete the assignment.

#### 📌 


- Improved Discount Calculation Logic

- Support additional offer types like cashback, exchange offers, tiered discounts, and time-bound deals and
Handle combinations (e.g., EMI + bank offer) more precisely.

- Validation & Error Handling

- Add input validation using @Validated, @NotNull, etc.

- Return structured error responses with clear messages and codes.

- Unit & Integration Testing

- Add test coverage for offer parsing, discount calculation, and endpoint logic using JUnit and MockMvc.

- Include edge case tests (e.g., invalid bank, empty offers).

- API Documentation

- Use Swagger/OpenAPI for clear, self-documented endpoints.

- Add logging, performance metrics, and tracking (e.g., most used banks, most effective offers).

- Security & Input Sanitization

- Protect endpoints with basic auth or token validation (if public).



##### TECH points
- Dockerise the app.
- Caching frequently used offers.
- Storing discount parameters explicitly in DB rather than parsing summary.
- Add Profiles to app for different Environments(DEV,QA,PROD etc).
- Scheduled jobs to ingest latest offers from Flipkart periodically.