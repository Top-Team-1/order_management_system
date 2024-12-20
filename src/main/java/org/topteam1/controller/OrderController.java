package org.topteam1.controller;

import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.Product;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.OrderRepository;
import org.topteam1.repository.ProductRepository;
import org.topteam1.service.OrderService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private Customer customer;
    private Product product;
    private final OrderService orderService;
    Scanner scanner = new Scanner(System.in);
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    public OrderController(OrderService orderService, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Метод запускает взаимодействие с сущностью Заказ
     */
    public void start() {
        while (true) {
            System.out.println(">>>>Управление заказами<<<<\n" +
                    "1) Создать заказ \n " +
                    "2) Показать все заказы\n " +
                    "3) Изменить статус заказа\n" +
                    "0) Назад");
            int choise = scanner.nextInt();
            scanner.nextLine();
            switch (choise) {
                case 1 -> createOrder();
                case 2 -> showAllOrders();
                case 3 -> changeOrderStatus();
                case 0 -> {
                    return;
                }
            }
        }
    }

    /**
     * Метод создания заказа, с выбором покупателя и товара
     */
    public void createOrder() {
        System.out.println("Выберите покупателя");
        System.out.println(customerRepository.findCustomer());

        int choiseCustomer = scanner.nextInt();
        customer = customerRepository.findCustomerForId(choiseCustomer);

        scanner.nextLine();

        System.out.println("Выберите товар");
        System.out.println(productRepository.findAll());

        int choiseProduct = scanner.nextInt();
        product = productRepository.returnProduct(choiseProduct);
        scanner.nextLine();

        String info = orderService.addOrder(customer, product).toString();
        System.out.println(info);
    }

    /**
     * Метод выводит информацию о заказах
     */
    public void showAllOrders() {
        String info = orderService.getAllOrders().toString();
        System.out.println(info);
    }

    /**
     * Метод изменения статуса заказа
     */
    public void changeOrderStatus() {
        int orderStatus;
        System.out.println("Выберите id заказа: ");

        int idOrder = scanner.nextInt();
        scanner.nextLine();

        Order order = orderService.getAllOrders().stream().filter(o -> o.getId() == idOrder).findFirst().get();
        System.out.println("Выберите статус заказа: \n1.PROCESSING\n 2.COMPLETED\n 3.CANCELLED ");

        orderStatus = scanner.nextInt();
        switch (orderStatus) {
            case 1 -> order.setOrderStatus("PROCESSING");
            case 2 -> order.setOrderStatus("COMPLETED");
            case 3 -> order.setOrderStatus("CANCELLED");
            default -> order.setOrderStatus("НЕПРАВИЛЬНЫЙ СТАТУС");
        }

        String info = orderService.toString();
        System.out.println(info);
    }
}
