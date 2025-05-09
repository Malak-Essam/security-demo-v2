# Spring Security Demo with JPA and JWT Authentication

A comprehensive Spring Boot application demonstrating:
- Database-backed user authentication
- JWT token-based authentication for APIs
- Spring Security's default login form
- Role-based access control (USER/ADMIN)

## Features

- **Dual Authentication Modes** 🔐
  - Browser-based form login (Spring Security default)
  - JWT authentication for API endpoints
- **Database Security** 🗄️
  - H2 in-memory database
  - User/Role entities with JPA
  - BCrypt password encoding
- **API Protection** 🛡️
  - Public endpoints
  - User-only endpoints
  - Admin-only endpoints
- **Session Management** 🔄
  - Stateless JWT authentication
  - CSRF protection disabled for APIs

## Prerequisites

- Java 17+
- Maven 3.6+
- IDE (IntelliJ, Eclipse, etc.)

## Quick Start

1. **Clone and Build**
```bash
git clone https://github.com/your-repo/spring-security-demo.git
cd spring-security-demo
mvn clean install
```
2. **Run the Application**
```bash
mvn spring-boot:run
```
