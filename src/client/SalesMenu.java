package client;

import java.util.Scanner;

import client.controller.OrderController;
import server.service.AnalizeService;

public class SalesMenu {

    private Scanner scanner;
    private OrderController orderController;
    private AnalizeService analizeService;

    public SalesMenu(Scanner scanner) {

        System.out.println("SalesMenu 생성");

        this.scanner = scanner;
        this.orderController = new OrderController();
        this.analizeService = new AnalizeService();
    }

    public void showMenu() {

        while(true) {

            System.out.println("\n===== ORDER & ANALYSIS =====");
            System.out.println("1. Order Menu");
            System.out.println("2. Sales By Time");
            System.out.println("3. Sales By Category");
            System.out.println("4. Sales By Age");
            System.out.println("0. Back");

            String input = scanner.nextLine();

            switch(input) {

                case "1":
                    orderController.login();
                    break;

                case "2":
                    analizeService.analizeAboutTime();
                    break;

                case "3":
                    analizeService.analizeAboutCategory();
                    break;

                case "4":
                    System.out.println("4번 선택됨");
                    SalesMenu salesMenu = new SalesMenu(scanner);
                    salesMenu.showMenu();
                    break;

                case "0":
                    return;

                default:
                    System.out.println("잘못된 입력");
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SalesMenu menu = new SalesMenu(scanner);
        menu.showMenu();
    }
}