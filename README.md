# 📦 Part B: Product Management Feature (Team Member B)

This component is responsible for handling the **Product Database Design** and implementing the backend logic for product management using a text-based console menu in Java.

## 🛠️ Tech Stack & Concepts Applied
- **Language:** Java (JDK 17+)
- **Database:** MySQL 8.0+
- **Driver:** MySQL Connector/J (JDBC)
- **Security:** `PreparedStatement` is fully utilized to guarantee defense against **SQL Injection attacks** during dynamic user inputs.
- **Architecture:** Text-based interactive CRUD system using `java.util.Scanner`.

---

## 🗄️ 1. Database Schema Design
The `Product` table is structured to efficiently store store catalog items and dynamically links with the Order system based on product classification.

```sql
CREATE TABLE Product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL
);