/**
 * Класс реализует главное меню программы.
 */
package org.topteam1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    private final ProductController productController;
    private final CustomerController customerController;
    private final OrderController orderController;
    Scanner sc = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    public MainController(ProductController productController, CustomerController customerController, OrderController orderController) {
        this.productController = productController;
        this.customerController = customerController;
        this.orderController = orderController;
    }

    /**
     * Метод для запуска программы.
     */
    public void start() {
        log.info("Работа с программой началась");
        while (true) {
            try {
                System.out.println("""
                        >>>>Главное меню<<<<
                        1) Работа с товаром
                        2) Работа с покупателем
                        3) Работа с заказом
                        0) Выход из программы""");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> productController.start();
                    case 2 -> customerController.start();
                    case 3 -> orderController.start();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Неверный выбор, попробуйте ещё раз");
                }
            } catch (InputMismatchException e) {
                log.error("Некорректный формат данных");
                System.out.println("Неверный выбор, попробуйте ещё раз");
                sc.nextLine();
            }
        }
    }
}
