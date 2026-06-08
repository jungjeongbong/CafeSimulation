


CREATE DATABASE IF NOT EXISTS project_db;
USE project_db;

DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL
);

SELECT * FROM Product;
