package org.topteam1.controller;

import org.topteam1.Exceptions.CustomerNotAddException;
import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.service.CustomerService;

import java.util.Scanner;

public class CustomerController {

    private final CustomerService customerService;

    Scanner sc = new Scanner(System.in);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Метод запускает взаимодействие с покупателем
     */
    public void start() {
        while (true) {
            int choice;
            System.out.println("""
                    >>>>Управление покупателями<<<<
                    1) Добавить покупателя\s
                    2) Посмотреть всех покупателей\s
                    3) Найти покупателя по ID
                    0) Назад""");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> getCustomerList();
                case 3 -> findCustomerId();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неизвестная команда, попробуйте ещё раз");
            }
        }
    }

    /**
     * Метод для работы с покупателем, добавлением его в список и присваиванием ему статуса
     */
    private void addCustomer() {
        int customerCategory;
        System.out.println("Введите Ваше имя: ");
        String customerName = sc.nextLine();
        System.out.println("""
                Выберите тип покупателя
                1) Новый покупатель
                2) Постоянный покупатель\s
                3) VIP покупатель\s""");
        customerCategory = sc.nextInt();
        String customerType;
        switch (customerCategory) {
            case 1 -> customerType = "Новый покупатель";
            case 2 -> customerType = "Постоянный покупатель";
            case 3 -> customerType = "VIP покупатель";
            default -> {
                System.out.println("Статус покупателя выбран неверно, попробуйте ещё раз");
                return;
            }
        }
        try {
            String info = customerService.addCustomer(customerName, customerType).toString();
            System.out.println(info);
        }catch (CustomerNotAddException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод отображает всех покупателей
     */
    private void getCustomerList() {
        String info = customerService.getCustomer().toString();
        System.out.println(info);
    }

    private void findCustomerId() {
        int findId;
        System.out.println("Введите ID покупателя для поиска");
        findId = sc.nextInt();
        sc.nextLine();
        try {
            String info = customerService.getCustomerForId(findId).toString();
            System.out.println(info);
        }catch (CustomerNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
