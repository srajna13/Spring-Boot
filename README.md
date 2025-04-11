# Digital Wallet System - Spring Boot

A simple Spring Boot project simulating a Digital Wallet system. It supports wallet creation, fund management, payments, and transaction history. The project uses an in-memory **H2 database** and supports testing via **Postman**.

---

## Features

- Create Wallets by Username
- Add Funds
- Make Payments
- View Wallet by ID
- View All Wallets
- Delete Wallet
- Track Transactions (Payment / Credit)
- Error handling with JSON responses (e.g., Insufficient Balance, Wallet Not Found)

---

##  Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database (In-memory)
- RESTful APIs
- Postman (API testing)
- Lombok

---

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
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: (leave blank)

Click Connect to explore tables - WALLET and TRANSACTIONS.

## API Endpoints(Use with Postman)
Base URL: http://localhost:8080/api/wallets

### Create Wallet 
```
POST /createWallet?username=JohnDoe
```
### Get All Wallets
```
GET /allWallets
```
### Get Wallet by ID 
```
GET /getWalletById/{id}

```
### Add Funds
```
POST /{id}/add?amount=500.0
```

### Make Payment
```
POST /{id}/pay?amount=100.0

```
If insufficient balance:
```
{
  "message": "Insufficient balance to make this transaction!",
  "status": 400
}
```

### Delete Wallet
```
POST /{id}/delete
```

---
## Testing with Postman
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
