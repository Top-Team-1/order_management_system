/**
 * Класс реализует главное меню программы.
 */
package org.topteam1.controller;

import java.util.Scanner;

public class MainController {
    private final ProductController productController;
    private final CustomerController customerController;
    private final OrderController orderController;
    Scanner sc = new Scanner(System.in);

    public MainController(ProductController productController, CustomerController customerController, OrderController orderController) {
        this.productController = productController;
        this.customerController = customerController;
        this.orderController = orderController;
    }

    /**
     * Метод для запуска программы.
     */
    public void start() {
        while (true) {
            System.out.println(">>>>Главное меню<<<<\n" +
                    "1)Работа с товаром\n" +
                    "2)Работа с покупателем\n" +
                    "3)Работа с заказом\n" +
                    "0)Выход из программы");
            int choise = sc.nextInt();
            sc.nextLine();
            switch (choise) {
                case 1 -> productController.start();
                case 2 -> customerController.start();
                case 3 -> orderController.start();
                case 0 -> {
                    return;
                }
            }
        }
    }
}
