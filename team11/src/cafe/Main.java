package cafe;

import java.util.Scanner;

/**
 * Main
 * 카페 주문 시스템 메인 진입점
 * 팀 11조 - 김민서, 신지원, 정유민, Twetar Myantnoe Htike
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   Cafe Order System & Customer Analysis  ");
        System.out.println("              데이터베이스 11조              ");
        System.out.println("==========================================");

        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Product Management (상품 관리)");
            System.out.println("2. Store Management   (매장 관리)");
            System.out.println("3. Order & Analysis   (주문/분석)");
            System.out.println("0. Exit");
            System.out.println("================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    ProductMenu productMenu = new ProductMenu(scanner);
                    productMenu.showMenu();
                    break;
                case "2":
                    StoreMenu storeMenu = new StoreMenu(scanner);
                    storeMenu.showMenu();
                    break;
                case "3":
                    SalesMenu salesMenu = new SalesMenu(scanner);
                    salesMenu.showMenu();
                    break;
                case "0":
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}