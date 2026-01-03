# 🐞 Bug Tracker Application

A full-stack **Bug Tracking System** built to manage bugs efficiently with secure authentication and role-based access control.  
Admins can create and assign bugs, while users can view and complete assigned bugs.

This project focuses on **clean backend architecture, security, and performance testing**, making it suitable for real-world use and backend interviews.



## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Role-based access:
  - **Admin**
  - **User**

### 🧑‍💼 Admin Features
- Create bugs
- Assign bugs to users
- View all bugs
- Track bug status (Open / Completed / In Progress)

### 👤 User Features
- View assigned bugs
- Mark bugs as completed
- Secure logout


## 🧰 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Hibernate / JPA

### Frontend
- React

### Testing & Tools
- Postman (API testing)
- Apache JMeter (Load & performance testing)
- Git & GitHub
- IntelliJ IDEA
- Linux


## 📐 Architecture

The application follows a **layered architecture**:

- **Controller Layer** – Handles API requests
- **Service Layer** – Business logic
- **Repository Layer** – Database operations
- **Security Layer** – JWT validation & role-based authorization

This structure improves **maintainability, scalability, and testability**.


## 🔄 Application Flow

1. User/Admin logs in
2. Backend generates a JWT token
3. Token is sent with each protected request
4. Backend verifys token and role
5. Authorized action is performed
6. User logs out and token is deleted from localStorage


## 📊 Performance Testing

- Load testing performed using **Apache JMeter**
- Measured:
  - Average response time
  - Percentiles (90th, 95th, 99th)
  - Throughput
  - Error rate

These results serve as a **baseline** for future optimizations using Redis and Kafka.



## 📁 Project Structure

<img width="404" height="435" alt="image" src="https://github.com/user-attachments/assets/e3b444e5-031f-4716-a386-fd016c4908d6" />



