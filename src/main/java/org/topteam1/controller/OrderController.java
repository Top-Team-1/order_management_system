package org.topteam1.controller;

import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.Exceptions.OrderNotAddException;
import org.topteam1.Exceptions.OrderNotFoundException;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.Product;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.ProductRepository;
import org.topteam1.service.OrderService;

import java.util.Scanner;

public class OrderController {
    private Customer customer;
    private Product product;
    private final OrderService orderService;
    Scanner scanner = new Scanner(System.in);
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

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
            System.out.println("""
                    >>>>Управление заказами<<<<
                    1) Создать заказ
                    2) Показать заказ по ID
                    3) Показать все заказы
                    4) Изменить статус заказа
                    0) Назад""");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> createOrder();
                case 2 -> showOrder();
                case 3 -> showAllOrders();
                case 4 -> changeOrderStatus();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неизвестная команда, попробуйте ещё раз");
            }
        }
    }

    /**
     * Метод создания заказа, с выбором покупателя и товара
     */
    public void createOrder() {
        System.out.println("Выберите покупателя");
        System.out.println(customerRepository.findCustomer());

        int choiceCustomer = scanner.nextInt();
        try {
            customer = customerRepository.findCustomerForId(choiceCustomer);

            scanner.nextLine();

            System.out.println("Выберите товар");
            System.out.println(productRepository.findAll());

            int choiceProduct = scanner.nextInt();

            product = productRepository.returnProduct(choiceProduct);
            scanner.nextLine();

            String info = orderService.addOrder(customer, product).toString();
            System.out.println(info);
        } catch (CustomerNotFoundException | ProductNotFoundException | OrderNotAddException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showOrder() {
        int findID;
        System.out.println("Введите ID товара для поиска");
        findID = scanner.nextInt();
        scanner.nextLine();
        try {
            String info = orderService.getOrder(findID).toString();
            System.out.println(info);
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
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
        try {
            int orderStatus;
            System.out.println("Выберите id заказа: ");

            int idOrder = scanner.nextInt();
            scanner.nextLine();

            Order order = orderService.getOrder(idOrder);
            System.out.println("Выберите статус заказа: \n1.PROCESSING\n 2.COMPLETED\n 3.CANCELLED ");

            orderStatus = scanner.nextInt();
            switch (orderStatus) {
                case 1 -> order.setOrderStatus("PROCESSING");
                case 2 -> order.setOrderStatus("COMPLETED");
                case 3 -> order.setOrderStatus("CANCELLED");
                default -> {
                    System.out.println("Неверный статус заказа");
                    return;
                }
            }

            String info = orderService.toString();
            System.out.println(info);
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
