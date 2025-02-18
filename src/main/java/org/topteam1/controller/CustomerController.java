package org.topteam1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.CustomerFileNotFoundException;
import org.topteam1.Exceptions.CustomerNotAddException;
import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.service.CustomerService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    Scanner sc = new Scanner(System.in);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Метод запускает взаимодействие с покупателем
     */
    public void start() {
        log.info("Запущена работа с покупателем");
        while (true) {
            try {
                System.out.println("""
                        >>>>Управление покупателями<<<<
                        1) Добавить покупателя\s
                        2) Посмотреть всех покупателей\s
                        3) Найти покупателя по ID
                        0) Назад""");
                int choice = sc.nextInt();
                sc.nextLine();

                log.info("Пользователь выбрал пункт меню: {}", choice);
                switch (choice) {
                    case 1 -> addCustomer();
                    case 2 -> getCustomerList();
                    case 3 -> findCustomerById();
                    case 0 -> {
                        log.info("Завершение работы с покупателем");
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
     * Метод для работы с покупателем и добавлением его в список
     */
    private void addCustomer() {
        log.info("Добавление покупателя");
        String customerName;
        do {
            System.out.println("Введите Ваше имя: ");
            customerName = sc.nextLine();
            if (!customerName.matches("^[А-Яа-яЁё]+$")) {
                log.warn("Имя покупателя содержит символы, цифры или буквы иного алфавита, или пустое{}", customerName);
                System.out.println("Имя должно состоять только из букв русского алфавита");
                customerName = null;
            }
        } while (customerName == null);

        try {
            String info = customerService.addCustomer(customerName).toString();
            log.info("Пользователь ввел: name={}", customerName);
            System.out.println(info);
        } catch (CustomerNotAddException e) {
            log.error("Ошибка при добавлении покупателя");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод отображает всех покупателей
     */
    private void getCustomerList() {

        log.info("Получение всех покупателей");

        try {
            customerService.getAllCustomers().forEach(System.out::println);
        } catch (CustomerFileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод выполняет поиск покупателя по ID
     */
    private void findCustomerById() {
        log.info("Поиск покупателя по id");
        System.out.println("Введите ID покупателя для поиска");
        int findId = sc.nextInt();
        sc.nextLine();
        try {
            String info = customerService.getCustomerById(findId).toString();
            System.out.println(info);
        } catch (CustomerNotFoundException e) {
            log.warn("Ошибка поиска покупателя по id");
            System.out.println(e.getMessage());
        }
    }
}
