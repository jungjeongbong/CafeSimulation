-- ====================================================================
-- 아래 기능 구현 및 분석 쿼리들은 콘솔 테스트용으로 필요할 때 블록 잡고 실행하세요.
-- 플레이스홀더(?)가 포함되어 있어 통으로 실행 시 에러가 나므로 구문만 유지합니다.
-- ====================================================================

/*
-- 신규 메뉴 insert
INSERT INTO product (product_name, category, unit_price) VALUES ('Macha Espresso', 'Coffee', 5500);

-- 시간대별 주문량 분석 실행 예시
SELECT HOUR(sales_timestamp) AS order_hour, COUNT(*) AS order_count FROM sales GROUP BY HOUR(sales_timestamp) ORDER BY order_hour;

-- 매장별 총 매출 순위 조회
SELECT store_name, SUM(amount) AS total_amount FROM view_order_detail GROUP BY store_name ORDER BY total_amount DESC;
*/