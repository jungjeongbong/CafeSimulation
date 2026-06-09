package cafe;

import java.sql.*;
import java.util.Scanner;

/**
 * StoreMenu
 * 매장 정보 관리 기능 담당 (신지원)
 */
public class StoreMenu {

    private final Scanner scanner;

    public StoreMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n==============================");
            System.out.println("       매장 관리 메뉴");
            System.out.println("==============================");
            System.out.println("1. 전체 매장 조회");
            System.out.println("2. 도시별 매장 조회");
            System.out.println("3. 도시별 매장 수 분석");
            System.out.println("4. 신규 매장 추가");
            System.out.println("5. 매장 정보 수정");
            System.out.println("6. 매장 삭제");
            System.out.println("0. 이전 메뉴로");
            System.out.println("------------------------------");
            System.out.print("선택: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": selectAllStores();    break;
                case "2": selectStoresByCity(); break;
                case "3": countStoresByCity();  break;
                case "4": insertStore();        break;
                case "5": updateStore();        break;
                case "6": deleteStore();        break;
                case "0": return;
                default:  System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void selectAllStores() {
        String sql = "SELECT store_id, store_name, city, manager_name FROM store ORDER BY store_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n----------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-15s %-15s%n", "Store ID", "Store Name", "City", "Manager");
            System.out.println("----------------------------------------------------------------------");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-20s %-15s %-15s%n",
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("city"),
                        rs.getString("manager_name"));
            }
            if (!hasData) System.out.println("등록된 매장이 없습니다.");
            System.out.println("----------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("[오류] 매장 조회 실패: " + e.getMessage());
        }
    }

    private void selectStoresByCity() {
        System.out.print("조회할 도시명을 입력하세요 (예: Seoul): ");
        String city = scanner.nextLine().trim();

        String sql = "SELECT s.store_id, s.store_name, s.city, s.manager_name, " +
                     "COUNT(sa.sales_id) AS total_orders " +
                     "FROM store s " +
                     "LEFT JOIN sales sa ON s.store_id = sa.store_id " +
                     "WHERE s.city = ? " +
                     "GROUP BY s.store_id, s.store_name, s.city, s.manager_name " +
                     "ORDER BY s.store_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n----------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-15s %-15s %-10s%n", "Store ID", "Store Name", "City", "Manager", "Orders");
            System.out.println("----------------------------------------------------------------------");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-20s %-15s %-15s %-10d%n",
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("city"),
                        rs.getString("manager_name"),
                        rs.getInt("total_orders"));
            }
            if (!hasData) System.out.println("'" + city + "'에 등록된 매장이 없습니다.");
            System.out.println("----------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("[오류] 매장 조회 실패: " + e.getMessage());
        }
    }

    private void countStoresByCity() {
        String sql = "SELECT city, COUNT(*) AS store_count FROM store GROUP BY city ORDER BY store_count DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n------------------------------");
            System.out.printf("%-15s %-10s%n", "City", "Store Count");
            System.out.println("------------------------------");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-15s %-10d%n", rs.getString("city"), rs.getInt("store_count"));
            }
            if (!hasData) System.out.println("데이터가 없습니다.");
            System.out.println("------------------------------");
        } catch (SQLException e) {
            System.out.println("[오류] 분석 실패: " + e.getMessage());
        }
    }

    private void insertStore() {
        System.out.println("\n[신규 매장 추가]");
        System.out.print("매장 ID를 입력하세요: ");
        String idInput = scanner.nextLine().trim();
        System.out.print("매장 이름을 입력하세요: ");
        String storeName = scanner.nextLine().trim();
        System.out.print("도시를 입력하세요 (예: Seoul): ");
        String city = scanner.nextLine().trim();
        System.out.print("담당 매니저 이름을 입력하세요: ");
        String managerName = scanner.nextLine().trim();

        String sql = "INSERT INTO store (store_id, store_name, city, manager_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(idInput));
            pstmt.setString(2, storeName);
            pstmt.setString(3, city);
            pstmt.setString(4, managerName);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✓ 매장 '" + storeName + "'이(가) 추가되었습니다.");
        } catch (NumberFormatException e) {
            System.out.println("[오류] 매장 ID는 숫자로 입력해주세요.");
        } catch (SQLException e) {
            System.out.println("[오류] 매장 추가 실패: " + e.getMessage());
        }
    }

    private void updateStore() {
        System.out.println("\n[매장 정보 수정]");
        System.out.print("수정할 매장 ID를 입력하세요: ");
        String idInput = scanner.nextLine().trim();
        System.out.print("새 매장 이름을 입력하세요: ");
        String storeName = scanner.nextLine().trim();
        System.out.print("새 도시를 입력하세요: ");
        String city = scanner.nextLine().trim();
        System.out.print("새 담당 매니저 이름을 입력하세요: ");
        String managerName = scanner.nextLine().trim();

        String sql = "UPDATE store SET store_name = ?, city = ?, manager_name = ? WHERE store_id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, storeName);
            pstmt.setString(2, city);
            pstmt.setString(3, managerName);
            pstmt.setInt(4, Integer.parseInt(idInput));
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✓ 매장 정보가 수정되었습니다.");
            } else {
                conn.rollback();
                System.out.println("해당 ID의 매장을 찾을 수 없습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[오류] 매장 ID는 숫자로 입력해주세요.");
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } catch (SQLException e) {
            System.out.println("[오류] 매장 수정 실패: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void deleteStore() {
        System.out.println("\n[매장 삭제]");
        System.out.print("삭제할 매장 ID를 입력하세요: ");
        String idInput = scanner.nextLine().trim();
        System.out.print("정말 삭제하시겠습니까? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("삭제가 취소되었습니다.");
            return;
        }

        String sql = "DELETE FROM store WHERE store_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(idInput));
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✓ 매장이 삭제되었습니다.");
            else System.out.println("해당 ID의 매장을 찾을 수 없습니다.");
        } catch (NumberFormatException e) {
            System.out.println("[오류] 매장 ID는 숫자로 입력해주세요.");
        } catch (SQLException e) {
            System.out.println("[오류] 매장 삭제 실패: " + e.getMessage());
        }
    }
}