# 💰 Finance Dashboard Backend

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
  <img src="https://img.shields.io/badge/H2-In--Memory%20DB-003545?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
</p>

<p align="center">
  A RESTful backend system for managing financial records with role-based access control, built with Spring Boot as part of a backend developer assessment.
</p>

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Architecture](#-project-architecture)
- [Role-Based Access Control](#-role-based-access-control)
- [API Endpoints](#-api-endpoints)
- [Dashboard Summary](#-dashboard-summary)
- [Validation & Error Handling](#-validation--error-handling)
- [Getting Started](#-getting-started)
- [Database](#-database)
- [Assumptions](#-assumptions)
- [Assignment Coverage](#-assignment-coverage)
- [Future Improvements](#-future-improvements)

---

## 📖 Overview

This project is the backend for a **Finance Dashboard System** where users with different roles interact with financial data. It focuses on:

- Clean layered backend architecture
- Role-based access control (RBAC) enforced at the service layer
- RESTful API design for CRUD and dashboard analytics
- Proper validation and error handling
- ORM-based data persistence using JPA/Hibernate

---

## ✅ Features

### 👤 User Management
- Create users and assign roles (`ADMIN`, `ANALYST`, `VIEWER`)
- Retrieve all users
- Manage user status (`ACTIVE` / `INACTIVE`)

### 💰 Financial Records Management
- Full CRUD operations on financial records
- Supported fields:

  | Field      | Type              | Description                      |
  |------------|-------------------|----------------------------------|
  | `amount`   | Decimal           | Transaction value (must be > 0)  |
  | `type`     | `INCOME/EXPENSE`  | Type of financial entry          |
  | `category` | String            | Category of the transaction      |
  | `date`     | Date              | Date of the transaction          |
  | `notes`    | String (optional) | Additional description           |

### 📊 Dashboard Analytics
- Total income, total expenses, net balance
- Aggregated summary returned as structured JSON

### 🔐 Role-Based Access Control
- Access restrictions enforced in the **service layer**
- Clear separation of what each role can and cannot do

---

## 🛠 Tech Stack

| Technology       | Purpose                            |
|------------------|------------------------------------|
| Java 21          | Core programming language          |
| Spring Boot 3.x  | Backend application framework      |
| Spring Data JPA  | Database abstraction layer         |
| Hibernate ORM    | JPA implementation                 |
| H2 Database      | In-memory relational database      |
| Maven            | Build and dependency management    |

---

## 🏗 Project Architecture

```
Client
  │
  ▼
Controller Layer      ──→  Handles HTTP requests & responses
  │
  ▼
Service Layer         ──→  Business logic & RBAC enforcement
  │
  ▼
Repository Layer      ──→  Data access via Spring Data JPA
  │
  ▼
H2 In-Memory DB       ──→  Stores users & financial records
```

### 📂 Package Structure

```
src/main/java/com/example/finance/
│
├── controller/
│   ├── UserController.java
│   ├── RecordController.java
│   └── DashboardController.java
│
├── service/
│   ├── UserService.java
│   ├── RecordService.java
│   └── DashboardService.java
│
├── repository/
│   ├── UserRepository.java
│   └── FinancialRecordRepository.java
│
├── model/
│   ├── User.java
│   └── FinancialRecord.java
│
└── FinanceApplication.java
```

---

## 🔐 Role-Based Access Control

Access control is enforced at the **service layer** to ensure consistent behavior across all API calls.

| Operation                  | ADMIN | ANALYST | VIEWER |
|----------------------------|:-----:|:-------:|:------:|
| Create financial records   | ✅    | ❌      | ❌     |
| View financial records     | ✅    | ✅      | ✅     |
| Update financial records   | ✅    | ❌      | ❌     |
| Delete financial records   | ✅    | ❌      | ❌     |
| Access dashboard summary   | ✅    | ✅      | ❌     |
| Manage users               | ✅    | ❌      | ❌     |

> Role is passed as a query parameter (`?role=ADMIN`) for simplicity. See [Assumptions](#-assumptions).

---

## 📡 API Endpoints

### 👤 Users

| Method | Endpoint | Description        |
|--------|----------|--------------------|
| `POST` | `/users` | Create a new user  |
| `GET`  | `/users` | Get all users      |

**Create User — Request Body:**
```json
{
  "name": "Vinay Kumar",
  "email": "vinay@example.com",
  "role": "ANALYST",
  "status": "ACTIVE"
}
```

---

### 💰 Financial Records

| Method   | Endpoint                        | Description              | Required Role |
|----------|---------------------------------|--------------------------|:-------------:|
| `POST`   | `/records?role=ADMIN`           | Create a new record      | `ADMIN`       |
| `GET`    | `/records`                      | Get all records          | Any           |
| `PUT`    | `/records/{id}?role=ADMIN`      | Update a record by ID    | `ADMIN`       |
| `DELETE` | `/records/{id}?role=ADMIN`      | Delete a record by ID    | `ADMIN`       |

**Create Record — Request Body:**
```json
{
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "April salary"
}
```

---

### 📊 Dashboard

| Method | Endpoint              | Description                      | Required Role        |
|--------|-----------------------|----------------------------------|----------------------|
| `GET`  | `/dashboard/summary`  | Get aggregated financial summary | `ADMIN`, `ANALYST`   |

---

## 📊 Dashboard Summary

The `/dashboard/summary` endpoint returns aggregated financial data:

```json
{
  "totalIncome": 7000.00,
  "totalExpense": 2000.00,
  "netBalance": 5000.00
}
```

---

## ⚠️ Validation & Error Handling

- `amount` must be a **positive** value
- `type`, `amount`, and `date` are **required fields**
- Requests with missing or invalid data return structured error responses
- Unauthorized role operations return access-denied messages with appropriate HTTP status codes

**Example error response:**
```json
{
  "error": "Access Denied",
  "message": "Only ADMIN can perform this operation",
  "status": 403
}
```

---

## ▶️ Getting Started

### Prerequisites

- Java 21+
- Maven 3.6+
- IntelliJ IDEA (recommended) or any Java IDE

### Setup & Run

**1. Clone the repository:**
```bash
git clone https://github.com/your-username/finance-dashboard-backend.git
cd finance-dashboard-backend
```

**2. Build the project:**
```bash
mvn clean install
```

**3. Run the application:**
```bash
mvn spring-boot:run
```
Or run `FinanceApplication.java` directly from your IDE.

**4. Server starts at:**
```
http://localhost:8080
```

---

## 🗄️ Database

H2 in-memory database is used for simplicity and portability.

| Table                | Description                              |
|----------------------|------------------------------------------|
| `users`              | Stores user profiles, roles, and status  |
| `financial_records`  | Stores all financial transactions        |

**H2 Console** (available during development):
```
http://localhost:8080/h2
```
> JDBC URL: `jdbc:h2:mem:testdb` | Username: `sa` | Password: *(leave blank)*

---

## 📌 Assumptions

| Assumption | Reason |
|------------|--------|
| Role is passed as a query parameter (`?role=ADMIN`) | Keeps implementation focused on RBAC logic; authentication is out of scope |
| H2 in-memory database is used | Simplifies setup without requiring external DB configuration |
| No JWT / session-based authentication | Focus is on backend logic and access control design, not auth flows |
| Data resets on server restart | Expected behavior with H2 in-memory; acceptable for assessment scope |

---

## 📋 Assignment Coverage

| Requirement                             | Status       |
|-----------------------------------------|--------------|
| User & role management                  | ✅ Completed  |
| Financial records CRUD                  | ✅ Completed  |
| Dashboard summary APIs                  | ✅ Completed  |
| Role-based access control               | ✅ Completed  |
| Input validation & error handling       | ✅ Completed  |
| Data persistence (H2 / JPA)             | ✅ Completed  |
| Filtering by category / type / date     | 🔄 Partial   |
| Pagination                              | ❌ Planned   |
| Authentication (JWT)                    | ❌ Planned   |
| Unit / Integration tests                | ❌ Planned   |

---

## 🚀 Future Improvements

- [ ] JWT-based authentication and token validation
- [ ] Pagination and filtering for financial records (by date, category, type)
- [ ] Category-wise and monthly trend analytics
- [ ] Soft delete functionality for records
- [ ] Unit tests (JUnit 5) and integration tests (MockMvc)
- [ ] Swagger / OpenAPI documentation
- [ ] Migration to PostgreSQL or MySQL for production readiness

---

## 👨‍💻 Author

**Vinay Kumar Korakoppula**
📧 vinaykumar22ra1a6694@gmail.com

---

*Submitted as part of the Backend Developer Intern Assessment — Zorvyn FinTech Pvt. Ltd.*
