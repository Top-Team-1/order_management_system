package org.topteam1;

import org.topteam1.controller.CustomerController;
import org.topteam1.controller.MainController;
import org.topteam1.controller.OrderController;
import org.topteam1.controller.ProductController;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.OrderRepository;
import org.topteam1.repository.ProductRepository;
import org.topteam1.service.CustomerService;
import org.topteam1.service.OrderService;
import org.topteam1.service.ProductService;


public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository("src/main/java/org/topteam1/repository/products.txt");
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);

        CustomerRepository customerRepository = new CustomerRepository("src/main/java/org/topteam1/repository/customers.txt");
        CustomerService customerService = new CustomerService(customerRepository);
        CustomerController customerController = new CustomerController(customerService);

        OrderRepository orderRepository = new OrderRepository();
        OrderService orderService = new OrderService(orderRepository);
        OrderController orderController = new OrderController(orderService, productRepository, customerRepository);

        MainController mainController = new MainController(productController, customerController, orderController);
        mainController.start();


    }
}
