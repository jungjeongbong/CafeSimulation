# ☕ Cafe Order System & Customer Analysis
**데이터베이스 11조** | 김민서, 신지원, 정유민, Twetar Myantnoe Htike

---

## 📁 Project Structure
```
team11-cafe-db/
├── sql/
│   ├── createschema.sql   # 테이블, 뷰, 인덱스 생성
│   ├── initdata.sql       # 초기 데이터 삽입
│   └── dropschema.sql     # 전체 테이블/뷰 삭제
├── src/
│   └── cafe/
│       ├── Main.java          # 메인 메뉴 진입점
│       ├── DBConnection.java  # DB 연결 관리
│       ├── ProductMenu.java   # 상품 관리 기능
│       ├── CustomerMenu.java  # 고객 관리 기능
│       ├── StoreMenu.java     # 매장 관리 기능
│       ├── SalesMenu.java     # 주문/판매 기능
│       └── AnalysisMenu.java  # 분석 쿼리 기능
├── docs/                  # 보고서, ER 다이어그램 등
└── README.md
```

---

## ⚙️ How to Run

### 1. 사전 준비
- Java 11 이상 설치
- MySQL Connector/J (JDBC 드라이버) 준비
- Oracle MySQL HeatWave 접속 정보 준비

### 2. 데이터베이스 초기화
MySQL에 접속 후 아래 순서로 실행:
```sql
source sql/createschema.sql
source sql/initdata.sql
```

### 3. DB 연결 정보 설정
`src/cafe/DBConnection.java` 에서 아래 정보 수정:
```java
private static final String URL  = "jdbc:mysql://<host>:<port>/<database>";
private static final String USER = "your_username";
private static final String PASS = "your_password";
```

### 4. 컴파일 및 실행
```bash
# 컴파일
javac -cp .:mysql-connector-j-*.jar src/cafe/*.java

# 실행
java -cp .:mysql-connector-j-*.jar cafe.Main
```

### 5. JAR 실행 (제출용)
```bash
java -cp cafe-system.jar:mysql-connector-j-*.jar cafe.Main
```
> **Main class: `cafe.Main`**

---

## 🗂️ Database Tables
| 테이블 | 설명 | 담당 |
|--------|------|------|
| product | 카페 메뉴 상품 정보 | 김민서 |
| customer | 고객 정보 | 김민서 |
| store | 매장 정보 | 신지원 |
| market_basket | 장바구니 항목 | 정유민 |
| sales | 판매 거래 기록 | 김민서 |
| total_sales | 장바구니별 총 금액 | 신지원 |
| customer_history | 고객 정보 변경 이력 (REQ14) | Twetar |
| product_history | 상품 가격 변경 이력 (REQ13) | Twetar |

---

## 📋 Application Menus
| 메뉴 | 기능 | REQ |
|------|------|-----|
| 신규 메뉴 추가 | INSERT product | REQ5 |
| 신규 고객 등록 | INSERT customer | REQ5 |
| 연령대별 주문 분석 | SELECT + GROUP BY + VIEW | REQ6, REQ7 |
| 시간대별 주문 분석 | SELECT + GROUP BY + VIEW | REQ6, REQ7 |
| 고객 정보 수정 | UPDATE (transaction) | REQ8, REQ12 |
| 메뉴 가격 수정 | UPDATE (transaction) | REQ8, REQ12, REQ13 |
| 고객 삭제 | DELETE | REQ9 |
| 메뉴 삭제 | DELETE | REQ9 |
| 가격 변경 전후 분석 | SELECT + JOIN + VIEW | REQ6, REQ13 |
| 고객 정보 변경 전후 분석 | SELECT + JOIN + VIEW | REQ6, REQ14 |
