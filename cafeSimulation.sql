-- createschema.sql
-- =========================================


CREATE TABLE product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE customer (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(20),
    city VARCHAR(50),
    age INT
);

CREATE TABLE store (
    store_id INT PRIMARY KEY,
    store_name VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    manager_name VARCHAR(100)
);

CREATE TABLE market_basket (
    basket_item_id INT PRIMARY KEY,
    market_basket_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE sales (
    sales_id INT PRIMARY KEY,
    sales_timestamp DATETIME NOT NULL,
    store_id INT NOT NULL,
    customer_id INT NOT NULL,
    market_basket_id INT NOT NULL,
    FOREIGN KEY (store_id) REFERENCES store(store_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE total_sales (
    total_sales_id INT AUTO_INCREMENT PRIMARY KEY,
    market_basket_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL
);

CREATE TABLE customer_history (
    customer_history_id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    name VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    city VARCHAR(50),
    age INT,
    start_timestamp DATETIME NOT NULL,
    end_timestamp DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE product_history (
    product_history_id INT PRIMARY KEY,
    product_id INT NOT NULL,
    product_name VARCHAR(100),
    category VARCHAR(50),
    unit_price_history DECIMAL(10,2) NOT NULL,
    start_timestamp DATETIME NOT NULL,
    end_timestamp DATETIME,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- 추가 index
CREATE INDEX idx_product_category ON product(category);
CREATE INDEX idx_customer_age ON customer(age);
CREATE INDEX idx_sales_timestamp ON sales(sales_timestamp);
CREATE INDEX idx_market_basket_id ON market_basket(market_basket_id);
CREATE INDEX idx_customer_history_period ON customer_history(start_timestamp, end_timestamp);
CREATE INDEX idx_product_history_period ON product_history(start_timestamp, end_timestamp);

-- View 1: 주문 상세 View
CREATE VIEW view_order_detail AS
SELECT 
    s.sales_id,
    s.sales_timestamp,
    s.store_id,
    st.store_name,
    s.customer_id,
    c.name AS customer_name,
    c.age,
    c.city AS customer_city,
    mb.market_basket_id,
    p.product_id,
    p.product_name,
    p.category,
    mb.quantity,
    p.unit_price,
    mb.quantity * p.unit_price AS amount
FROM sales s
JOIN store st ON s.store_id = st.store_id
JOIN customer c ON s.customer_id = c.customer_id
JOIN market_basket mb ON s.market_basket_id = mb.market_basket_id
JOIN product p ON mb.product_id = p.product_id;

-- View 2: 장바구니별 총 매출 View
CREATE VIEW view_basket_total AS
SELECT
    mb.market_basket_id,
    SUM(mb.quantity * p.unit_price) AS total_amount
FROM market_basket mb
JOIN product p ON mb.product_id = p.product_id
GROUP BY mb.market_basket_id;

-- =========================================
-- initdata.sql
-- =========================================

INSERT INTO product VALUES
(1, 'Americano', 'Coffee', 4000),
(2, 'Cafe Latte', 'Coffee', 4500),
(3, 'Vanilla Latte', 'Coffee', 5000),
(4, 'Cold Brew', 'Coffee', 4800),
(5, 'Green Tea Latte', 'Non-Coffee', 5200),
(6, 'Chocolate Latte', 'Non-Coffee', 5000),
(7, 'Lemon Ade', 'Ade', 5500),
(8, 'Grapefruit Ade', 'Ade', 5500),
(9, 'Cheese Cake', 'Dessert', 6500),
(10, 'Chocolate Cake', 'Dessert', 6800);

INSERT INTO customer VALUES
(1, 'Kim Minseo', 'minseo@email.com', '010-1111-1111', 'Seoul', 22),
(2, 'Lee Jiwon', 'jiwon@email.com', '010-2222-2222', 'Seoul', 24),
(3, 'Park Hana', 'hana@email.com', '010-3333-3333', 'Incheon', 19),
(4, 'Choi Yuna', 'yuna@email.com', '010-4444-4444', 'Busan', 31),
(5, 'Jung Doyeon', 'doyeon@email.com', '010-5555-5555', 'Daegu', 28),
(6, 'Kang Sumin', 'sumin@email.com', '010-6666-6666', 'Seoul', 35),
(7, 'Yoon Seoyeon', 'seoyeon@email.com', '010-7777-7777', 'Gwangju', 21),
(8, 'Han Jisoo', 'jisoo@email.com', '010-8888-8888', 'Daejeon', 26),
(9, 'Oh Minji', 'minji@email.com', '010-9999-9999', 'Suwon', 42),
(10, 'Shin Nayeon', 'nayeon@email.com', '010-0000-0000', 'Seoul', 18);

INSERT INTO store VALUES
(1, 'Ewha Cafe', 'Seoul', 'Manager Kim'),
(2, 'Sinchon Cafe', 'Seoul', 'Manager Lee'),
(3, 'Hongdae Cafe', 'Seoul', 'Manager Park'),
(4, 'Gangnam Cafe', 'Seoul', 'Manager Choi'),
(5, 'Busan Cafe', 'Busan', 'Manager Jung'),
(6, 'Daegu Cafe', 'Daegu', 'Manager Kang'),
(7, 'Incheon Cafe', 'Incheon', 'Manager Yoon'),
(8, 'Suwon Cafe', 'Suwon', 'Manager Han'),
(9, 'Daejeon Cafe', 'Daejeon', 'Manager Oh'),
(10, 'Gwangju Cafe', 'Gwangju', 'Manager Shin');

INSERT INTO market_basket VALUES
(1, 1001, 1, 2),
(2, 1001, 9, 1),
(3, 1002, 2, 1),
(4, 1003, 3, 2),
(5, 1004, 4, 1),
(6, 1005, 5, 1),
(7, 1006, 6, 2),
(8, 1007, 7, 1),
(9, 1008, 8, 2),
(10, 1009, 10, 1),
(11, 1010, 1, 1),
(12, 1010, 2, 1);

INSERT INTO sales VALUES
(1, '2025-05-01 09:10:00', 1, 1, 1001),
(2, '2025-05-01 11:30:00', 2, 2, 1002),
(3, '2025-05-01 13:20:00', 3, 3, 1003),
(4, '2025-05-02 15:45:00', 4, 4, 1004),
(5, '2025-05-02 18:10:00', 5, 5, 1005),
(6, '2025-05-03 08:50:00', 6, 6, 1006),
(7, '2025-05-03 14:25:00', 7, 7, 1007),
(8, '2025-05-04 16:40:00', 8, 8, 1008),
(9, '2025-05-04 20:15:00', 9, 9, 1009),
(10, '2025-05-05 10:05:00', 10, 10, 1010);

ALTER TABLE market_basket
ADD COLUMN unit_price_at_order DECIMAL(10,2);

UPDATE market_basket mb
JOIN product p ON mb.product_id = p.product_id
SET mb.unit_price_at_order = p.unit_price;

-- total_sales는 직접 계산해서 넣기
INSERT INTO total_sales (market_basket_id, total_amount)
SELECT
    market_basket_id,
    SUM(quantity * unit_price_at_order) AS total_amount
FROM market_basket
GROUP BY market_basket_id;

INSERT INTO customer_history VALUES
(1, 1, 'Kim Minseo', 'minseo@email.com', '010-1111-1111', 'Seoul', 22, '2025-01-01 00:00:00', NULL),
(2, 2, 'Lee Jiwon', 'jiwon@email.com', '010-2222-2222', 'Seoul', 24, '2025-01-01 00:00:00', NULL),
(3, 3, 'Park Hana', 'hana@email.com', '010-3333-3333', 'Incheon', 19, '2025-01-01 00:00:00', NULL),
(4, 4, 'Choi Yuna', 'yuna@email.com', '010-4444-4444', 'Busan', 31, '2025-01-01 00:00:00', NULL),
(5, 5, 'Jung Doyeon', 'doyeon@email.com', '010-5555-5555', 'Daegu', 28, '2025-01-01 00:00:00', NULL),
(6, 6, 'Kang Sumin', 'sumin@email.com', '010-6666-6666', 'Seoul', 35, '2025-01-01 00:00:00', NULL),
(7, 7, 'Yoon Seoyeon', 'seoyeon@email.com', '010-7777-7777', 'Gwangju', 21, '2025-01-01 00:00:00', NULL),
(8, 8, 'Han Jisoo', 'jisoo@email.com', '010-8888-8888', 'Daejeon', 26, '2025-01-01 00:00:00', NULL),
(9, 9, 'Oh Minji', 'minji@email.com', '010-9999-9999', 'Suwon', 42, '2025-01-01 00:00:00', NULL),
(10, 10, 'Shin Nayeon', 'nayeon@email.com', '010-0000-0000', 'Seoul', 18, '2025-01-01 00:00:00', NULL);

INSERT INTO product_history VALUES
(1, 1, 'Americano', 'Coffee', 4000, '2025-01-01 00:00:00', NULL),
(2, 2, 'Cafe Latte', 'Coffee', 4500, '2025-01-01 00:00:00', NULL),
(3, 3, 'Vanilla Latte', 'Coffee', 5000, '2025-01-01 00:00:00', NULL),
(4, 4, 'Cold Brew', 'Coffee', 4800, '2025-01-01 00:00:00', NULL),
(5, 5, 'Green Tea Latte', 'Non-Coffee', 5200, '2025-01-01 00:00:00', NULL),
(6, 6, 'Chocolate Latte', 'Non-Coffee', 5000, '2025-01-01 00:00:00', NULL),
(7, 7, 'Lemon Ade', 'Ade', 5500, '2025-01-01 00:00:00', NULL),
(8, 8, 'Grapefruit Ade', 'Ade', 5500, '2025-01-01 00:00:00', NULL),
(9, 9, 'Cheese Cake', 'Dessert', 6500, '2025-01-01 00:00:00', NULL),
(10, 10, 'Chocolate Cake', 'Dessert', 6800, '2025-01-01 00:00:00', NULL);

-- =========================================
-- Product 기능
-- =========================================

-- 신규 메뉴 insert
INSERT INTO product (product_id, product_name, category, unit_price)
VALUES (?, ?, ?, ?);

-- 신규 메뉴 insert 시 product_history에도 현재 가격 기록
INSERT INTO product_history
(product_history_id, product_id, product_name, category, unit_price_history, start_timestamp, end_timestamp)
VALUES (?, ?, ?, ?, ?, NOW(), NULL);

-- 전체 메뉴 조회
SELECT * FROM product;

-- 카테고리별 메뉴 조회
SELECT *
FROM product
WHERE category = ?;

-- 기존 메뉴 가격 변경
START TRANSACTION;

UPDATE product_history
SET end_timestamp = NOW()
WHERE product_id = ?
  AND end_timestamp IS NULL;

UPDATE product
SET unit_price = ?
WHERE product_id = ?;

INSERT INTO product_history
(product_history_id, product_id, product_name, category, unit_price_history, start_timestamp, end_timestamp)
SELECT ?, product_id, product_name, category, unit_price, NOW(), NULL
FROM product
WHERE product_id = ?;

COMMIT;

-- 메뉴 삭제
DELETE FROM product
WHERE product_id = ?;

-- 카테고리별 상품 수
SELECT category, COUNT(*) AS product_count
FROM product
GROUP BY category;
 
-- =========================================
-- Customer 기능
-- =========================================

-- 신규 회원 insert
INSERT INTO customer
(customer_id, name, email, phone_number, city, age)
VALUES (?, ?, ?, ?, ?, ?);

-- 신규 회원 insert 시 customer_history에도 기록
INSERT INTO customer_history
(customer_history_id, customer_id, name, email, phone_number, city, age, start_timestamp, end_timestamp)
VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), NULL);

-- 전체 회원 조회
SELECT * FROM customer;

-- 특정 도시 고객 조회
SELECT *
FROM customer
WHERE city = ?;

-- 가격 변경 업데이트
START TRANSACTION;

UPDATE product_history
SET end_timestamp = NOW()
WHERE product_id = ?
  AND end_timestamp IS NULL;

UPDATE product
SET unit_price = ?
WHERE product_id = ?;

INSERT INTO product_history
(product_history_id, product_id, product_name, category, unit_price_history, start_timestamp, end_timestamp)
SELECT ?, product_id, product_name, category, unit_price, NOW(), NULL
FROM product
WHERE product_id = ?;

COMMIT;

-- 고객 정보 변경
START TRANSACTION;

UPDATE customer_history
SET end_timestamp = NOW()
WHERE customer_id = ?
  AND end_timestamp IS NULL;

UPDATE customer
SET name = ?,
    email = ?,
    phone_number = ?,
    city = ?,
    age = ?
WHERE customer_id = ?;

INSERT INTO customer_history
(customer_history_id, customer_id, name, email, phone_number, city, age, start_timestamp, end_timestamp)
SELECT ?, customer_id, name, email, phone_number, city, age, NOW(), NULL
FROM customer
WHERE customer_id = ?;

COMMIT;

-- 회원 삭제
DELETE FROM customer
WHERE customer_id = ?;

-- 연령대별 고객 수
SELECT 
    CASE
        WHEN age < 20 THEN '10s'
        WHEN age BETWEEN 20 AND 29 THEN '20s'
        WHEN age BETWEEN 30 AND 39 THEN '30s'
        WHEN age BETWEEN 40 AND 49 THEN '40s'
        ELSE '50s or older'
    END AS age_group,
    COUNT(*) AS customer_count
FROM customer
GROUP BY age_group;

-- =========================================
-- Store 기능
-- =========================================

INSERT INTO store (store_id, store_name, city, manager_name)
VALUES (?, ?, ?, ?);

SELECT * FROM store;

UPDATE store
SET store_name = ?,
    city = ?,
    manager_name = ?
WHERE store_id = ?;

DELETE FROM store
WHERE store_id = ?;

-- 도시별 매장 수
SELECT city, COUNT(*) AS store_count
FROM store
GROUP BY city;

-- =========================================
-- Sales / Market_Basket / Total_sales 기능
-- =========================================

-- 주문 insert
INSERT INTO sales
(sales_id, sales_timestamp, store_id, customer_id, market_basket_id)
VALUES (?, NOW(), ?, ?, ?);

-- 장바구니 item insert
INSERT INTO market_basket
(basket_item_id, market_basket_id, product_id, quantity, unit_price_at_order)
SELECT
    ?, ?, p.product_id, ?, p.unit_price
FROM product p
WHERE p.product_id = ?;

-- total_sales 계산해서 insert
INSERT INTO total_sales (total_sales_id, market_basket_id, total_amount)
SELECT ?, mb.market_basket_id, SUM(mb.quantity * p.unit_price)
FROM market_basket mb
JOIN product p ON mb.product_id = p.product_id
WHERE mb.market_basket_id = ?
GROUP BY mb.market_basket_id;

-- 주문 상세 조회
SELECT *
FROM view_order_detail
WHERE sales_id = ?;

-- 주문 삭제
DELETE FROM sales
WHERE sales_id = ?;

-- 장바구니 상품 삭제
DELETE FROM market_basket
WHERE basket_item_id = ?;

-- =========================================
-- 분석 Query
-- =========================================

-- 1. 시간대별 주문량 분석
SELECT 
    HOUR(sales_timestamp) AS order_hour,
    COUNT(*) AS order_count
FROM sales
GROUP BY HOUR(sales_timestamp)
ORDER BY order_hour;

-- 2. 시간대별 카테고리 주문량 분석
SELECT
    HOUR(v.sales_timestamp) AS order_hour,
    v.category,
    SUM(v.quantity) AS total_quantity
FROM view_order_detail v
GROUP BY HOUR(v.sales_timestamp), v.category
ORDER BY order_hour, total_quantity DESC;

-- 3. 연령대별 주문 카테고리 및 주문량 분석
SELECT
    CASE
        WHEN age < 20 THEN '10s'
        WHEN age BETWEEN 20 AND 29 THEN '20s'
        WHEN age BETWEEN 30 AND 39 THEN '30s'
        WHEN age BETWEEN 40 AND 49 THEN '40s'
        ELSE '50s or older'
    END AS age_group,
    category,
    SUM(quantity) AS total_quantity,
    SUM(amount) AS total_sales_amount
FROM view_order_detail
GROUP BY age_group, category
ORDER BY age_group, total_quantity DESC;

-- 4. 카테고리별 총 매출
SELECT 
    category,
    SUM(quantity) AS total_quantity,
    SUM(amount) AS total_amount
FROM view_order_detail
GROUP BY category
ORDER BY total_amount DESC;

-- 5. 매장별 총 매출
SELECT
    store_name,
    SUM(amount) AS total_amount
FROM view_order_detail
GROUP BY store_name
ORDER BY total_amount DESC;

-- 6. 고객별 총 구매 금액
SELECT
    customer_id,
    customer_name,
    SUM(amount) AS total_spent
FROM view_order_detail
GROUP BY customer_id, customer_name
ORDER BY total_spent DESC;

-- 7. 고객 정보 변경 전후 판매 분석
SELECT
    ch.customer_id,
    ch.name,
    ch.city,
    ch.age,
    ch.start_timestamp,
    ch.end_timestamp,
    COUNT(s.sales_id) AS order_count,
    SUM(ts.total_amount) AS total_spent
FROM customer_history ch
JOIN sales s 
    ON ch.customer_id = s.customer_id
   AND s.sales_timestamp >= ch.start_timestamp
   AND (ch.end_timestamp IS NULL OR s.sales_timestamp < ch.end_timestamp)
JOIN total_sales ts
    ON s.market_basket_id = ts.market_basket_id
WHERE ch.customer_id = ?
GROUP BY 
    ch.customer_history_id,
    ch.customer_id,
    ch.name,
    ch.city,
    ch.age,
    ch.start_timestamp,
    ch.end_timestamp
ORDER BY ch.start_timestamp;

-- 8. 상품 가격 변경 전후 판매 분석
SELECT
    p.product_id,
    p.product_name,
    mb.unit_price_at_order AS price_at_order,
    SUM(mb.quantity) AS total_quantity,
    SUM(mb.quantity * mb.unit_price_at_order) AS total_sales_amount
FROM market_basket mb
JOIN product p ON mb.product_id = p.product_id
WHERE p.product_id = ?
GROUP BY p.product_id, p.product_name, mb.unit_price_at_order
ORDER BY mb.unit_price_at_order;

-- =========================================
-- dropschema.sql
-- =========================================

DROP VIEW IF EXISTS view_basket_total;
DROP VIEW IF EXISTS view_order_detail;

DROP TABLE IF EXISTS total_sales;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS market_basket;
DROP TABLE IF EXISTS product_history;
DROP TABLE IF EXISTS customer_history;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS product;
