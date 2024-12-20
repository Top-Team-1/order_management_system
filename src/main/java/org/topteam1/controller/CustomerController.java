package org.topteam1.controller;

import org.topteam1.service.CustomerService;

import java.util.Scanner;

public class CustomerController {

    private final CustomerService customerService;

    Scanner sc = new Scanner(System.in);
    private Integer customerId;
    private String customerName;
    private String customerType;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Метод запускает взаимодействие с покупателем
     */
    public void start() {
        while (true) {
            Integer choise;
            System.out.println("Выберите опцию");
            System.out.println("1) Добавить покупателя \n" +
                    "2) Посмотреть всех покупателей \n" +
                    "3) Найти покупателя по ID");
            choise = sc.nextInt();
            sc.nextLine();
            switch (choise) {
                case 1 -> addCustomer();
                case 2 -> getCustomerList();
                case 3 -> findCustomerId();
                default -> System.out.println("Некорректный выбор");
            }
            break;
        }
    }

    /**
     * Метод для работы с покупателем, добавлением его в список и присваиванием ему статуса
     */
    private void addCustomer() {
        Integer customerCategory = 0;
        System.out.println("Введите Ваше имя: ");
        customerName = sc.nextLine();
        System.out.println("""
                Выберите тип покупателя
                1) Новый покупатель
                2) Постоянный покупатель\s
                3) VIP покупатель\s""");
        customerCategory = sc.nextInt();
        switch (customerCategory) {
            case 1 -> customerType = "Новый покупатель";
            case 2 -> customerType = "Постоянный покупатель";
            case 3 -> customerType = "VIP покупатель";
            default -> customerType = "Неверный тип покупателя";
        }
        String info = customerService.addCustomer(customerName, customerType).toString();
        System.out.println(info);
    }

    /**
     * Метод отображает всех покупателей
     */
    private void getCustomerList() {
        String info = customerService.getCustomer().toString();
        System.out.println(info);
    }

    private void findCustomerId() {
        Integer findId;
        System.out.println("Введите ID покупателя для поиска");
        findId = sc.nextInt();
        sc.nextLine();
        String info = customerService.getCustomerForId(findId).toString();
        System.out.println(info);
    }
}
