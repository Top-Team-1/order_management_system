package org.topteam1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.*;
import org.topteam1.model.Customer;
import org.topteam1.model.OrderStatus;
import org.topteam1.model.Product;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.ProductRepository;
import org.topteam1.service.CustomerService;
import org.topteam1.service.OrderService;
import org.topteam1.service.ProductService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private Customer customer;
    private Product product;
    private final OrderService orderService;
    Scanner sc = new Scanner(System.in);
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService,
                           ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    /**
     * Метод запускает взаимодействие с сущностью Заказ
     */
    public void start() {
        log.info("Запущена работа с заказом");
        while (true) {
            try {
                System.out.println("""
                        >>>>Управление заказами<<<<
                        1) Создать заказ
                        2) Показать заказ по ID
                        3) Показать все заказы
                        4) Изменить статус заказа
                        0) Назад""");
                int choice = sc.nextInt();
                sc.nextLine();
                log.info("Пользователь выбрал пункт меню: {}", choice);
                switch (choice) {
                    case 1 -> addOrder();
                    case 2 -> findOrderById();
                    case 3 -> getOrderList();
                    case 4 -> changeOrderStatus();
                    case 0 -> {
                        log.info("Завершение работы с товаром");
                        return;
                    }
                    default -> System.out.println("Неизвестная команда, попробуйте ещё раз");
                }
            } catch (InputMismatchException e) {
                log.error("Некорректный формат данных");
                System.out.println("Неверный выбор, попробуйте ещё раз");
                sc.nextLine();
            }
        }
    }

    /**
     * Метод создания заказа, с выбором покупателя и товара
     */
    public void addOrder() {
        log.info("Создание заказа");
        System.out.println("Выберите покупателя");
        System.out.println(customerRepository.findAll());

        int choiceCustomer = sc.nextInt();
        try {
            customer = customerService.getCustomerById(choiceCustomer);
            log.info("Найден покупатель с ID: {}", choiceCustomer);

            sc.nextLine();

            System.out.println("Выберите товар");
            System.out.println(productRepository.findAll());

            int choiceProduct = sc.nextInt();

            product = productService.getProductById(choiceProduct);
            log.info("Найден товар с ID: {}", choiceProduct);
            sc.nextLine();

            String info = orderService.addOrder(customer, product).toString();
            log.info("Заказ успешно создан: {}", info);
            System.out.println(info);
        } catch (CustomerNotFoundException | ProductNotFoundException | OrderNotAddException e) {
            log.error("Ошибка при создании заказа: {}", e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод выводит информацию заказа по ID
     */
    public void findOrderById() {
        int findID;
        log.info("Поиск заказа по id");
        System.out.println("Введите ID товара для поиска");
        findID = sc.nextInt();
        sc.nextLine();
        try {
            String info = orderService.getOrderById(findID).toString();
            System.out.println(info);
        } catch (OrderFileNotFoundException | OrderNotFoundException e) {
            log.warn("Ошибка поиска заказа по id ");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод выводит информацию о заказах
     */
    public void getOrderList() {
        log.info("Получение всех заказов");
        try {
            String info = orderService.getAllOrders().toString();
            System.out.println(info);
        } catch (OrderFileNotFoundException e) {
            log.warn("Ошибка получения заказов ");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод изменения статуса заказа
     */
    public void changeOrderStatus() {
        log.info("Начато изменение статуса заказа");
        try {
            System.out.println("Выберите id заказа: ");

            int idOrder = sc.nextInt();
            sc.nextLine();

            System.out.println("Выберите статус заказа:\n" +
                    "1)" + OrderStatus.PROCESSING.getRus() + "\n" +
                    "2)" + OrderStatus.COMPLETED.getRus() + "\n" +
                    "3)" + OrderStatus.CANCELED.getRus());

            int choice = sc.nextInt();
            sc.nextLine();

            String info = orderService.updateOrderStatus(idOrder, choice).toString();
            log.info("Статус заказа изменён: {}", info);
            System.out.println(info);
        } catch (OrderNotFoundException e) {
            log.warn("Заказ с ID {} не найден для обновления статуса", e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
