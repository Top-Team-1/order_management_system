package org.topteam1.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.topteam1.Exceptions.OrderNotFoundException;
import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.OrderStatus;
import org.topteam1.model.Product;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.OrderRepository;
import org.topteam1.repository.ProductRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;
    private CustomerService customerService;
    private ProductService productService;
    private Path newFilePath;


    @BeforeEach
    void setUp() throws IOException {
        newFilePath = Path.of("src/test/java/org/topteam1/orders_test.txt");
        Files.createFile(newFilePath);

        ProductRepository productRepository = new ProductRepository("src/main/java/org/topteam1/repository/products.txt");
        CustomerRepository customerRepository = new CustomerRepository("src/main/java/org/topteam1/repository/customers.txt");
        productService = new ProductService(productRepository);
        customerService = new CustomerService(customerRepository);
        OrderRepository orderRepository = new OrderRepository(newFilePath.toString());
        orderService = new OrderService(customerService, productService, orderRepository);

    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(newFilePath);
        Files.deleteIfExists(Path.of("src/test/java/org/topteam1/orders_test.txt_id"));
    }

    @Test
    void addOrder_Successful_Addition_Order() {
        //given
        Customer customer = customerService.getCustomerById(1);
        Product product = productService.getProductById(1);
        //when
        Order order = orderService.addOrder(customer, product);
        //then
        assertNotNull(order);
        assertEquals(order.getCustomer(), customer);
        assertEquals(order.getProduct(), product);
        assertEquals(1, orderService.getAllOrders().size());

    }

    @Test
    void getOrderById_Show_Return_Order() {
        //given
        Customer customer = customerService.getCustomerById(1);
        Product product = productService.getProductById(1);
        Order order = orderService.addOrder(customer, product);
        //when
        Order foundOrder = orderService.getOrderById(Math.toIntExact(order.getId()));
        //then
        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
        assertEquals(customerService.getCustomerById(1).getName(), foundOrder.getCustomer().getName());
    }

    @Test
    void getOrderById_Show_ThrowException_Order_NotFound() {
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(99));
    }

    @Test
    void updateOrderStatus() {
        //given
        Customer customer = customerService.getCustomerById(1);
        Product product = productService.getProductById(1);
        Order order = orderService.addOrder(customer, product);
        //when
        order.setOrderStatus(OrderStatus.COMPLETED);
        //then
        assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
    }

    @Test
    void getAllOrders_Show_Return_OrderList() {
        //given
        Customer customer1 = customerService.getCustomerById(1);
        Customer customer2 = customerService.getCustomerById(2);
        Product product1 = productService.getProductById(1);
        Product product2 = productService.getProductById(2);
        //when
        Order order1 = orderService.addOrder(customer1, product1);
        Order order2 = orderService.addOrder(customer2, product2);
        //then
        assertEquals(productService.getProductById(1).getName(), order1.getProduct().getName());
        assertEquals(productService.getProductById(2).getName(), order2.getProduct().getName());
    }
}