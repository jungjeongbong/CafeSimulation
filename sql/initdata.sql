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

-- total_sales는 직접 계산해서 넣기
INSERT INTO total_sales (total_sales_id, market_basket_id, total_amount)
SELECT 
    ROW_NUMBER() OVER (ORDER BY mb.market_basket_id) AS total_sales_id,
    mb.market_basket_id,
    SUM(mb.quantity * p.unit_price) AS total_amount
FROM market_basket mb
JOIN product p ON mb.product_id = p.product_id
GROUP BY mb.market_basket_id;

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

