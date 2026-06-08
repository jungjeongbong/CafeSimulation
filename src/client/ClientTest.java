package client;

import client.controller.OrderController;

public class ClientTest {
    public static void startOrder(String ip, OrderController orderController) {
        Client client = new Client();
        
        client.setOrderController(orderController);
        orderController.setClient(client);
        
        client.connect(ip, 50001); 
        client.listen();
    }
    
    public static void main(String[] args) {
        OrderController orderController = new OrderController();
        
        startOrder("localhost", orderController);
        orderController.login();
    }
}