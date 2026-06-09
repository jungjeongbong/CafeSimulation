package client.controller;

import server.controller.Basket;
import server.domain.Customer;
import server.repository.DBConnector;
import server.repository.MarketProductRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.Client;
import server.service.*;
import server.controller.ConnectionController;
import server.controller.Product;

public class OrderController {
	
    private MenuService menuService = new MenuService();
    private MarketProductRepository productRepository = new MarketProductRepository();
    private Client client;
    private Basket basket;
    private CheckAlreadyInDB checkService;
    private Customer customer;
    private CustomerService customerService;
    
    public OrderController() {

        System.out.println("OrderController 생성");

        this.customerService = new CustomerService();
        this.checkService = new CheckAlreadyInDB();
        this.basket = new Basket();

        client = new Client();

        client.setOrderController(this);
        setClient(client);

        client.connect("localhost", 50002);

        client.listen();
    }
    
    public void login() {

        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        try {

            System.out.print("주문하려면 전화번호를 입력하세요 : ");
            String phoneNumber = br.readLine();

            customer = customerService.login(phoneNumber);

            if (customer != null) {

                System.out.println(
                    customer.getName() + "님 로그인 성공");

                if (client != null) {
                    client.send(customer.getName() + " 로그인");
                }

                orderMenu();
            }
            else {

                System.out.println(
                    "등록되지 않은 전화번호입니다.");

                signUp(phoneNumber);

                orderMenu();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void signUp(String phoneNumber) {

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in));

        try {

            System.out.print("이름 : ");
            String name = br.readLine();

            System.out.print("도시 : ");
            String city = br.readLine();

            System.out.print("나이 : ");
            int age = Integer.parseInt(br.readLine());

            String sql =
                "INSERT INTO customer "
                + "(name, phone_number, city, age) "
                + "VALUES (?, ?, ?, ?)";

            try (Connection conn = DBConnector.connectDB();
                 PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

                pstmt.setString(1, name);
                pstmt.setString(2, phoneNumber);
                pstmt.setString(3, city);
                pstmt.setInt(4, age);

                pstmt.executeUpdate();

                System.out.println("회원가입 완료");
            }

            // 회원가입 후 로그인
            customer = customerService.login(phoneNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitOrder() {
        System.out.println("주문을 중단하고 연결을 종료합니다.");
        if (client != null) {
            client.send("disconnect");
        }
    }
    
    public void endConnect() {
        if (client != null) {
            client.disconnect();
        }
        System.exit(0);
    }
    
    public void processMessage(String msg) {
        if (msg.startsWith("disconnect")) {
            endConnect();
        }
    }
    
    public void giveDrinks(Customer customer) {
        if (client != null) {
            client.send(customer.getName() + "님 주문하신 음료 나왔습니다.");
        }
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public void orderMenu() {

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in));

        try {

            while(true) {

                menuService.showAllMenu();

                System.out.println(
                    "상품번호 입력(0=주문완료)");

                int productId =
                    Integer.parseInt(
                        br.readLine());

                if(productId == 0) {
                    break;
                }

                Product product =
                    productRepository
                    .findProductById(productId);

                if(product == null) {

                    System.out.println(
                        "없는 상품입니다.");

                    continue;
                }

                basket.putProductIn(product);

                System.out.println(
                    product.getProductName()
                    + " 담김");
            }

            basket.printBasket();

            completeOrder();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void completeOrder() {

        if(client == null || customer == null) {
            return;
        }

        int storeId = 1; // 임시

        StringBuilder sb = new StringBuilder();

        sb.append("ORDER:")
          .append(customer.getCustomerID())
          .append(":")
          .append(storeId)
          .append(":");

        for(int i=0;i<basket.getProducts().size();i++) {

            Product p = basket.getProducts().get(i);

            sb.append(p.getProductID())
              .append(",")
              .append(basket.getProduct_number().get(i))
              .append(";");
        }

        client.send(sb.toString());

        System.out.println("주문이 서버로 전송되었습니다.");
    }
    
    
}