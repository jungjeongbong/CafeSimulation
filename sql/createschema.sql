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

