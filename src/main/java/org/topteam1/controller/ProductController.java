/**
 * Класс контроллер для обработки запросов из консоли.
 */
package org.topteam1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.ProductFileNotFoundException;
import org.topteam1.Exceptions.ProductNotAddException;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.ProductCategory;
import org.topteam1.service.ProductService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    Scanner sc = new Scanner(System.in);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Метод запускает взаимодействие с сущностью "Товар".
     */
    public void start() {
        log.info("Запущена работа с товаром");
        while (true) {
            try {
                System.out.println("""
                        >>>>Управление товарами<<<<
                        1) Добавить товар
                        2) Посмотреть все доступные товары
                        3) Найти товар по ID
                        0) Назад""");
                int choice = sc.nextInt();
                sc.nextLine();
                log.info("Пользователь выбрал пункт меню: {}", choice);
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> getProductList();
                    case 3 -> findProductById();
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
     * Метод инициализирует поля класса Товар и добавляет его в список.
     */
    private void addProduct() {
        log.info("Добавление продукта");

        String productName;
        do {
            System.out.print("Введите название товара - ");
            productName = sc.nextLine();
            if (productName == null || productName.isBlank()) {
                log.warn("Некорректный формат для имени товара{}", productName);
                System.out.println("Название не может быть пустым");
            }
        } while (productName == null || productName.isBlank());

        int productPrice;
        do {
            System.out.print("Введите цену товара - ");
            productPrice = sc.nextInt();
            sc.nextLine();
            if (productPrice <= 0) {
                log.warn("Цена отрицательное число - {}", productPrice);
                System.out.println("Цена не может быть отрицательной");
            }
        } while (productPrice <= 0);

        int categoryNumber;
        System.out.println("Выберете категорию товара\n" +
                "1)" + ProductCategory.SMARTPHONE.getRus() + "\n" +
                "2)" + ProductCategory.TV.getRus() + "\n" +
                "3)" + ProductCategory.HOUSEHOLD_APPLIANCES.getRus());
        categoryNumber = sc.nextInt();

        try {
            log.info("Пользователь ввёл: name={}, price={}, category={}", productName, productPrice, ProductCategory.getProductByNumber(categoryNumber));
            String info = productService.addProduct(productName, productPrice, ProductCategory.getProductByNumber(categoryNumber)).toString();
            System.out.println(info);
        } catch (IllegalArgumentException | ProductNotAddException e) {
            log.error("Ошибка при добавлении товара");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод отображает все доступные товары.
     */
    private void getProductList() {
        log.info("Получение всех товаров");
        try {
            productService.getAllProducts().forEach(System.out::println);
        } catch (ProductFileNotFoundException e) {
            log.warn("Ошибка получения товаров ");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для поиска нужного товара по ID.
     */
    private void findProductById() {
        log.info("Поиск товара по id");
        System.out.println("Введите ID товара для поиска");
        int findID = sc.nextInt();
        sc.nextLine();
        try {
            String info = productService.getProductById(findID).toString();
            System.out.println(info);
        } catch (ProductFileNotFoundException | ProductNotFoundException e) {
            log.warn("Ошибка поиска товара по id ");
            System.out.println(e.getMessage());
        }
    }
}
