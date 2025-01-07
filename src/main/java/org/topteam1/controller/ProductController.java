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
            int choice;
            System.out.println("""
                    >>>>Управление товарами<<<<
                    1) Добавить товар
                    2) Посмотреть все доступные товары
                    3) Найти товар по ID
                    0) Назад""");
            choice = sc.nextInt();
            sc.nextLine();
            log.info("Пользователь выбрал пункт меню: {}", choice);
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> getProductList();
                case 3 -> findProduct();
                case 0 -> {
                    log.info("Завершение работы с товаром");
                    return;
                }
                default -> System.out.println("Неизвестная команда, попробуйте ещё раз");
            }
        }
    }

    /**
     * Метод инициализирует поля класса Товар и добавляет его в список.
     */
    private void addProduct() {
        log.info("Добавление продукта");
        int categoryNumber;
        System.out.print("Введите название товара - ");
        String productName = sc.nextLine();
        System.out.print("Введите цену товара - ");
        Integer productPrice = sc.nextInt();
        sc.nextLine();
        System.out.println("Выберете категорию товара\n" +
                "1)" + ProductCategory.SMARTPHONE.getRus() + "\n" +
                "2)" + ProductCategory.TV.getRus() + "\n" +
                "3)" + ProductCategory.HOUSEHOLD_APPLIANCES.getRus());
        categoryNumber = sc.nextInt();
        try {
//            String productCategory = String.valueOf(ProductCategory.getProductByNumber(categoryNumber).getRus());
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
            String info = productService.getAll().toString();
            System.out.println(info);
        } catch (ProductFileNotFoundException e) {
            log.warn("Ошибка получения товаров ");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для поиска нужного товара по ID.
     */
    private void findProduct() {
        int findID;
        log.info("Поиск товара по id");
        System.out.println("Введите ID товара для поиска");
        findID = sc.nextInt();
        sc.nextLine();
        try {
            String info = productService.getProduct(findID).toString();
            System.out.println(info);
        } catch (ProductFileNotFoundException | ProductNotFoundException e) {
            log.warn("Ошибка поиска товара по id ");
            System.out.println(e.getMessage());
        }
    }
}
