# Product & Order Management System API

## Overview

A RESTful API for managing products, orders, and users with JWT authentication and transaction handling. Developed as a Backend Assignment for **LightVision Inc**.

---

## Technology Stack

| Component         | Technology                           |
| ----------------- | ------------------------------------ |
| **Framework**     | Spring Boot 4.0.0                    |
| **Language**      | Java 17                              |
| **Database**      | MySQL                                |
| **ORM**           | Spring Data JPA                      |
| **Security**      | JWT , BCrypt                         |
| **Documentation** | SpringDoc OpenAPI 2.5.0 (Swagger UI) |
| **Build Tool**    | Maven                                |
| **Utilities**     | Lombok, Java Dotenv                  |

---

## Core Features

### 1. Product Management

- CRUD operations for products (name, price, stock, description)
- Stock deduction during order processing
- JWT authentication required

### 2. Order Processing

- **ACID Transactions**: All orders wrapped in `@Transactional`
- **Stock Validation**: Automatic rollback if insufficient stock
- **Multi-Item Orders**: Support multiple products per order

### 3. User Management & Security

- **Registration**: Username, email (unique), password (BCrypt), role
- **Validation**: Duplicate username/email detection
- **Login**: JWT token generation (24-hour expiration)
- **Protected Endpoints**: Products and orders require JWT authentication with Role-Based Access Control for permissions.

---

## Database Design

### Entity Relationships

- **Users ↔ Orders**: One-to-Many
- **Orders ↔ Products**: Many-to-Many (via `order_items`)
- **Orders ↔ OrderItems**: One-to-Many

### Main Tables

- **users**: id, username, email, password, role
- **products**: id, name, description, price, stock
- **orders**: id, user_id, total_amount, status, created_at
- **order_items**: id, order_id, product_id, quantity, price

---

## Installation and Setup

This guide will walk you through the steps to get the project up and running on your local machine.

### Prerequisites

Make sure you have the following software installed on your system:

- **Java 17+**
- **Maven**
- **MySQL 8.0+**

### 1. Clone the Repository

First, clone the project repository from GitHub to your local machine:

```bash
git clone https://github.com/your-username/product-and-order-api.git
cd product-and-order-api
```

### 2. Database Setup

The application requires a MySQL database to store product, order, and user data.

1.  **Start your MySQL server.**
2.  **Log in to MySQL**
3.  **Create a new database** for the application. The default name is `lightvision_db`.
    ```sql
    CREATE DATABASE lightvision_db;
    ```

### 3. Environment Configuration

The application uses a `.env` file to manage environment variables like database credentials and JWT secrets.

1.  **Create a `.env` file** in the root of the project by copying the example file:

    ```bash
    copy .env.example .env
    ```

2.  **Edit the `.env` file** with your specific configuration:

    ```properties
    # The full JDBC URL for your MySQL database
    DB_URL=jdbc:mysql://localhost:3306/lightvision_db

    # Your MySQL username and password
    DB_USERNAME=your_mysql_username
    DB_PASSWORD=your_mysql_password

    # A strong, secret key for signing JWTs (at least 32 characters long)
    JWT_SECRET=YOUR_SECRET_KEY_AT_LEAST_32_CHARACTERS_LONG
    ```

### 4. Build the Project

Use Maven to download dependencies and compile the source code.

```bash
mvn clean install
```

### 5. Run the Application

You can run the application from your IDE or the command line.

#### From the Command Line

Use the Maven wrapper script included in the project.

```bash
.\mvnw.cmd spring-boot:run
```

#### From an IDE (e.g., IntelliJ IDEA, Eclipse)

1.  Locate the `ProductAndOrderApiApplication.java` file in `src/main/java/com/ngominhthuan/product_and_order_api/`.
2.  Run the file.

Once started, the application will be running at **http://localhost:8080**.

### 6. Verify the Setup

To ensure the application is running correctly, open your web browser and navigate to the Swagger UI documentation:

**http://localhost:8080/swagger-ui/index.html**

You should see the API documentation page, which means the server is up and running. You can now use this interface to test the API endpoints.

---

## API Documentation

### Swagger UI

Access interactive API documentation at:

**http://localhost:8080/swagger-ui/index.html**

### Endpoints

#### Authentication (Public)

- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login (returns JWT token)

#### Products (Protected)

- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create product (Admin role required)
- `DELETE /api/products/{id}` - Delete product (Admin role required)

#### Orders (Protected)

- `POST /api/orders` - Create order

---

## Testing with Swagger UI

### 1. Register a User

```json
{
  "username": "testuser",
  "password": "password123",
  "email": "test@example.com",
  "role": "USER"
}
```

### 2. Login

```json
{
  "username": "testuser",
  "password": "password123"
}
```

Copy the returned JWT token.

### 3. Authorize

- Click "Authorize" button (Lock icon at the top left of the swagger page)
- Paste token in "Value" field
- Click "Authorize"
- Now you can access protected API

### 4. Create Product

```json
{
  "name": "Laptop Dell",
  "price": 1299,
  "stock": 10,
  "description": "High-performance laptop"
}
```

### 5. Create Order

```json
{
  "userId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

---

## Transaction Example

The system demonstrates ACID transactions with automatic rollback:

**Scenario**: Order 5 units of Product A (stock: 10) and 3 units of Product B (stock: 2)

**Result**: Entire transaction fails and rolls back because Product B has insufficient stock. No changes saved to database.

---

## Project Structure

```
product-and-order-api/
├── src/main/java/com/ngominhthuan/product_and_order_api/
│   ├── controller/          # REST Controllers
│   ├── model/               # JPA Entities
│   ├── repository/          # Data Access Layer
│   ├── service/             # Business Logic
│   ├── security/            # JWT & Security Config
│   ├── DTO/                 # Data Transfer Objects
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── ProductRequest.java
│   │   └── OrderRequest.java
│   └── ProductAndOrderApiApplication.java
├── src/main/resources/
│   └── application.properties
├── .env.example
├── pom.xml
└── README.md
```

---

## Configuration

### Key Settings

```properties
# Database
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000  # 24 hours
```

### Security

- **Public**: `/api/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**`
- **Protected**: All other endpoints require JWT in header: `Authorization: Bearer <token>`
- **Password Hashing**: BCrypt
- **Token Expiration**: 24 hours

---

## Author

**Ngo Minh Thuan**
Ho Chi Minh City University of Technology (HCMUT)
Backend Developer

---

## License

This project is developed as a technical assignment for LightVision Inc.
