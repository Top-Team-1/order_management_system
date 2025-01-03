package org.topteam1.controller;

import org.topteam1.Exceptions.CustomerFileNotFoundException;
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
     * Метод для работы с покупателем и добавлением его в список
     */
    private void addCustomer() {

        System.out.println("Введите Ваше имя: ");
        String customerName = sc.nextLine();
        try {
            String info = customerService.addCustomer(customerName).toString();
            System.out.println(info);
        } catch (CustomerNotAddException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод отображает всех покупателей
     */
    private void getCustomerList() {

        try {
            String info = customerService.getCustomer().toString();
            System.out.println(info);
        } catch (CustomerFileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод выполняет поиск покупателя по ID
     */
    private void findCustomerId() {
        int findId;
        System.out.println("Введите ID покупателя для поиска");
        findId = sc.nextInt();
        sc.nextLine();
        try {
            String info = customerService.getCustomerForId(findId).toString();
            System.out.println(info);
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeCustomerType() {
        try {

        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
