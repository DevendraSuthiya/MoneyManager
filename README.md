# 💰 MoneyManager

A Spring Boot–based application for managing personal finances, tracking income and expenses, and organizing financial data efficiently.

---

## 🚀 Features
- Add, update, and delete income/expense records.
- Categorize transactions.
- Secure authentication and authorization.
- RESTful API for integration with frontend or mobile apps.
- Configurable database connection via `application.properties` (ignored in Git for security).
- Email notification support.

---

## 🛠 Tech Stack
- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **Maven**
- **MySQL** (default, configurable to other DBs)

## 📂 Project Structure
```bash
src/
├── main/
│ ├── java/com/sharma/moneymanager/...
│ ├── resources/
│ ├── static/ # Static files (CSS, JS, images)
│ ├── templates/ # Thymeleaf templates (if applicable)
│ └── application.properties # Local configuration (ignored in Git)
└── test/
└── java/com/sharma/moneymanager/...
```

## ⚙️ Setup & Installation

### 1️⃣ Clone the repository
```bash
git clone https://github.com/your-username/moneymanager.git
cd moneymanager
```
### 2️⃣ Create application.properties
Create a file at src/main/resources/application.properties with the following template:
```bash
spring.application.name=MoneyManager
spring.datasource.url=db_url
spring.datasource.username=db_user_name
spring.datasource.password=db_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

server.servlet.context-path=/api/v1.0
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=google_account
spring.mail.password=password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.from=sender_email

# Frontend URL
money.manager.frontend.url=http://localhost:5173
```
### ▶️ Run the Application

