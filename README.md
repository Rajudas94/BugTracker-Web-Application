# 🐞 BugTracker Web Application

A full-stack **Bug Tracking System** built to manage bugs efficiently with secure authentication and role-based access control.  
Admins can create and assign bugs, while users can view and complete assigned bugs.

This project focuses on **clean backend architecture, security, and performance testing**, making it suitable for real-world use.

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

## 🧪 API Testing

- All REST APIs tested using **Postman**
- JWT-protected endpoints verified
- Role-based access validated

## 📌 Future Improvements

- Redis caching for faster data access ✅ 
- Kafka for asynchronous processing
- Advanced monitoring & metrics
- Pagination and filtering
- Dockerized deployment

## 📸 Project Screenshots

### Register / Login Page
<img width="1322" height="615" alt="Login and Register Page" src="https://github.com/user-attachments/assets/a2baea49-8995-4cc5-b854-e4cb23fae841" />

### Admin Dashboard Page
<img width="1322" height="615" alt="Admin Dashboard Page" src="https://github.com/user-attachments/assets/88293ec6-1443-49f2-b8c6-ae472e9f45e6" />

### Create Bug Page
<img width="1322" height="615" alt="Bug Creation Page" src="https://github.com/user-attachments/assets/29e29c0b-cf8c-4a62-b6a2-73e1d49a716e" />

### View / Assign Bugs to User Page
<img width="1322" height="615" alt="Bug Assignment To Users Page" src="https://github.com/user-attachments/assets/3347b7c9-61c4-4616-bd16-572c28712f1d" />

### Delete Bugs Page
<img width="1322" height="615" alt="Delete Bug Page" src="https://github.com/user-attachments/assets/dd96d436-52da-45d0-a986-77b4a48b0dfa" />

### User Dashboard Page
<img width="1322" height="615" alt="User Dashboard Page" src="https://github.com/user-attachments/assets/eb893412-a1e9-4154-9039-558aff2a8994" />

### Mark Bugs as Completed (User Side) Page
<img width="1322" height="615" alt="Users Marks Bug as Completed Page" src="https://github.com/user-attachments/assets/c56ec6f9-0e88-4d37-a751-0b41055df119" />

## 🎥 Demo Video
👉 [Click here to watch the demo video](https://drive.google.com/drive/u/2/folders/1Ko4zceLHxNtbDJ2-9E1jYVdQDrnBPLmy)














