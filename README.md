# Digital Wallet System - Spring Boot

A simple Spring Boot project simulating a Digital Wallet system. It supports wallet creation, fund management, payments, and transaction history. The project uses an in-memory **H2 database** and supports testing via **Postman**.

---

## Features

- Create Wallets by Username
- Add Funds
- Make Payments
- View Wallet by ID (admin only)
- View All Wallets (admin only)
- Delete Wallet (admin only)
- Track Transactions (Payment / Credit)
- Error handling with JSON responses (e.g., Insufficient Balance, Wallet Not Found, User already registered)

---

##  Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database (In-memory)
- RESTful APIs
- Postman (API testing)
- Lombok
- Spring Security
- Jwt
- JUnit
- Mockito
  
---

### Authentication & Authorization

This application utilizes JWT (JSON Web Tokens) for stateless authentication and enforces role-based access control.

##  Entity Overview

### `Wallet`
- `id` (Long): Primary Key
- `username` (String)
- `balance` (double)
- `List<Transactions>` (OneToMany)

### `Transactions`
- `id` (Long): Primary Key
- `type` (String): "Paid" or "Added"
- `amount` (double)
- `timestamp` (LocalDateTime)
- `wallet` (Wallet reference)

### `User`
- `id` (Long): Primary Key
- `username` (String)
- `password` (double)
- `isAdmin` (boolean)

---

### Admin Credentials

- **Username**: `admin`
- **Password**: `adminpassword`

Use these credentials to log in and obtain a JWT token for accessing admin protected endpoints.

---
## Project Setup

###  1. Clone the Repo

```bash
git clone <repository_url>
cd <project_folder>
```

### 2. Build and run
Make sure you have Java 17+ and Maven installed.
```bash
./mvnw spring-boot:run
```
App will be running at:
```
http://localhost:8080
```

## H2 Database Console
Access the H2 Console at:
```
http://localhost:8080/h2-console
```
- JDBC URL: jdbc:h2:mem:walletdb
- User Name: sa
- Password: (leave blank)

Click Connect to explore tables - Wallet, Users, Transactions.

## API Endpoints ( Use with Postman )
Base URL: http://localhost:8080/api/

### Public Endpoints

- **POST** `http://localhost:8080/api/auth/signup`

Request Body:
  ```
  {
    "username": "testuser",
    "password": "testpassword"
  }
  ```
  
  Response:
  ```
  {
    User registered successfully.
  }
  ```

- **POST** `http://localhost:8080/api/auth/login`  
  Authenticate a user and receive a JWT token.

  Request Body:
  ```
  {
    "username": "testuser",
    "password": "testpassword"
  }
  ```

  Response:
  ```
  {
    "token":"<your_jwt_token>"
  }
  ```

### Protected Endpoints

User Authentication Required:
### Create Wallet 
```
POST http://localhost:8080/api/wallets/createWallet?username=testuser
```
Headers:
- `Content-type`:`application/json`
- `Authorization`:`Bearer <your_jwt_token>`

Response:
  ```
  {
    "id": <id>,
    "username": "testuser",
    "balance": 0.0,
    "transactions": <your_transactions_list>
  }
  ```

### Get All Wallets
```
GET http://localhost:8080/api/wallets/allWallets
```
Headers:
- `Content-type`:`application/json`
- `Authorization`:`Bearer <admin_jwt_token>`


### Get Wallet by ID 
```
GET http://localhost:8080/api/wallets/getWalletById/{id}
```
Headers:
- `Content-type`:`application/json`
- `Authorization`:`Bearer <admin_jwt_token>`

### Add Funds
```
POST http://localhost:8080/api/wallets/{id}/add?amount=500.0
```
Headers:
- `Content-type`:`application/json`
- `Authorization`:`Bearer <your_jwt_token>`

### Make Payment
```
POST http://localhost:8080/api/wallets/{id}/pay?amount=100.0

```
Headers:
- `Content-type`:`application/json`
- `Authorization`:`Bearer <your_jwt_token>`

If insufficient balance:
```
{
  "message": "Insufficient balance to make this transaction!",
  "status": 400
}
```

### Delete Wallet
```
POST http://localhost:8080/api/wallets/{id}/delete
```
Headers:
- `Content-type`:`application/json`
- `Authorization`:`Bearer <admin_jwt_token>`
---

## Testing endpoints with Postman
1. Open Postman.
2. Use the provided endpoints with `http://localhost:8080/api/wallets` as base URL.
3. Set HTTP method (POST or GET).
4. Check response body and status code.
5. You can view database changes live in the H2 console

---

## Exception Handling

- WalletNotFoundException → 404 NOT FOUND
```
{
  "message": "Wallet not found",
  "status": 404
}
```

-InsufficientBalanceException → 400 BAD REQUEST
```
{
  "message": "Insufficient balance to make this transaction!",
  "status": 400
}
```

-UserAlreadyExistsException → 409 Conflict
```
{
  "message": "User already registered!",
  "status": 409
}
```
---

## Running Tests
to run unit tests:
```
mvn test
```
This will:
- Compile test classes from src/test/java
- Execute all JUnit 5 / Mockito-based test cases
- Print results in the console
