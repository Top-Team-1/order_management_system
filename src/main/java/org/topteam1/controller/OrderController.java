package org.topteam1.controller;

import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.Exceptions.OrderNotAddException;
import org.topteam1.Exceptions.OrderNotFoundException;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.Customer;
import org.topteam1.model.OrderStatus;
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
        System.out.println(customerRepository.findAll());

        int choiceCustomer = scanner.nextInt();
        try {
            customer = customerRepository.find(choiceCustomer);

            scanner.nextLine();

            System.out.println("Выберите товар");
            System.out.println(productRepository.findAll());

            int choiceProduct = scanner.nextInt();

            product = productRepository.find(choiceProduct);
            scanner.nextLine();

            String info = orderService.addOrder(customer, product).toString();
            System.out.println(info);
        } catch (CustomerNotFoundException | ProductNotFoundException | OrderNotAddException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод выводит информацию заказа по ID
     */
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
            System.out.println("Выберите id заказа: ");

            int idOrder = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Выберите статус заказа:\n" +
                    "1)" + OrderStatus.PROCESSING.getRus() + "\n" +
                    "2)" + OrderStatus.COMPLETED.getRus() + "\n" +
                    "3)" + OrderStatus.CANCELED.getRus());

            int choice = scanner.nextInt();
            scanner.nextLine();

            String info = orderService.updateOrderStatus(idOrder, choice).toString();
            System.out.println(info);
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
