import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/project_db?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "twelTar@#11"; //

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== PRODUCT MANAGEMENT MENU =====");
            System.out.println("1. Add New Product (Insert)");
            System.out.println("2. View All Products (Select)");
            System.out.println("3. Update Product Price (Update)");
            System.out.println("4. Delete Product (Delete)");
            System.out.println("5. Exit");
            System.out.print("Select an option (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 5) {
                System.out.println("Exiting system. Goodbye!");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter unit price: ");
                    double price = scanner.nextDouble();

                    addProduct(name, category, price);
                    break;

                case 2:
                    viewProducts();
                    break;

                case 3:
                    System.out.print("Enter Product ID to update: ");
                    int updateId = scanner.nextInt();
                    System.out.print("Enter new unit price: ");
                    double newPrice = scanner.nextDouble();

                    updateProductPrice(updateId, newPrice);
                    break;

                case 4:
                    System.out.print("Enter Product ID to delete: ");
                    int deleteId = scanner.nextInt();

                    deleteProduct(deleteId);
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
        scanner.close();
    }


    public static void addProduct(String name, String category, double price) {
        String sql = "INSERT INTO Product (product_name, category, unit_price) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            System.out.println("Success: Product added successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void viewProducts() {
        String sql = "SELECT * FROM Product";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Current Product List ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("ID: " + rs.getInt("product_id") +
                        " | Name: " + rs.getString("product_name") +
                        " | Category: " + rs.getString("category") +
                        " | Price: $" + rs.getDouble("unit_price"));
            }
            if (!hasData) {
                System.out.println("No products found in the database.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void updateProductPrice(int id, double newPrice) {
        String sql = "UPDATE Product SET unit_price = ? WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Success: Product price updated successfully!");
            } else {
                System.out.println("Warning: Product ID not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Success: Product deleted successfully!");
            } else {
                System.out.println("Warning: Product ID not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}