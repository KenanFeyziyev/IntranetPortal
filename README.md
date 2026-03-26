# 🚀 Intranet Portal

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Status](https://img.shields.io/badge/Status-Active-success)

A scalable and modular **Spring Boot REST API** designed for managing employees, departments, attendance, and work permits within an organization.

---

## 📌 Overview

This project is built using **clean architecture principles** and follows a layered structure (Controller → Service → Repository).
It simulates a real-world intranet backend system with advanced business logic beyond basic CRUD operations.

👉 Frontend Application:
https://github.com/KenanFeyziyev/IntranetPortal-Frontend

---

## ✨ Features

* 👨‍💼 Employee management (CRUD + business logic)
* 🏢 Department & Position management
* ⏰ Attendance tracking (check-in via cardCode)
* 📄 Work permit management (approval workflow)
* 🎂 Monthly birthday detection with custom logic
* 💰 Salary calculation with tax rules
* 📊 Monthly work permit duration reporting

---

## 🏗️ Architecture

* Layered Architecture (Controller → Service → Repository)
* DTO-based request/response design
* MapStruct for object mapping
* Global exception handling
* Logging with SLF4J

---

## 🛠️ Technologies

* **Java 21**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **Liquibase** (database migrations)
* **MapStruct**
* **Gradle**

---

## 🔐 Security (In Progress)

* Spring Security integration
* JWT-based authentication
* Role-based authorization (Admin/User)
* Endpoint access control

---

## 📬 API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ⚙️ Setup & Run

```bash
git clone https://github.com/KenanFeyziyev/IntranetPortal.git
cd IntranetPortal
./gradlew bootRun
```

---

## 👨‍💻 Author

**Kenan Feyziyev**
Backend Developer (Java / Spring Boot)
