/**
 * Класс контроллер для обработки запросов из консоли.
 */
package org.topteam1.controller;

import org.topteam1.service.ProductService;

import java.util.Scanner;

public class ProductController {
    private final ProductService productService;

    Scanner sc = new Scanner(System.in);
    private String productName;
    private Integer productPrice;
    private String productCategory;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Метод запускает взаимодействие с сущностью "Товар".
     */
    public void start() {
        while (true) {
            int choise;
            System.out.println("Выберете опцию");
            System.out.println("1)Добавить товар\n" +
                    "2)Посмотреть все доступные товары\n" +
                    "3)Найти товар по ID");
            choise = sc.nextInt();
            sc.nextLine();
            switch (choise) {
                case 1 -> addProduct();
                case 2 -> getProductList();
                case 3 -> findProduct();
                default -> System.out.println("Некорректный выбор"); // Бросим исключение

            }
        }

    }

    /**
     * Метод инициализирует поля класса Товар и добавляет его в список.
     */
    private void addProduct() {
        int categoryNumber = 0;
        System.out.print("Введите название товара - ");
        productName = sc.nextLine();
        System.out.print("Введите цену товара - ");
        productPrice = sc.nextInt();
        sc.nextLine();
        System.out.println("""
                Введите категорию товара
                1)Смартфоны
                2)Телевизоры\s
                3)Бытовая техника \s""");
        categoryNumber = sc.nextInt();
        switch (categoryNumber) {
            case 1 -> productCategory = "Смартфоны";
            case 2 -> productCategory = "Телевизоры";
            case 3 -> productCategory = "Бытовая техника";
            default ->
                    productCategory = "Неизвестная категория"; // Тут будем бросать исключения, когда их добавим, пока так.
        }
        String info = productService.addProduct(productName, productPrice, productCategory).toString();
        System.out.println(info);
    }

    /**
     * Метод отображает все доступные товары.
     */
    private void getProductList() {
        String info = productService.getAll().toString();
        System.out.println(info);
    }

    /**
     * Метод для поиска нужного товара по ID.
     */
    private void findProduct() {
        int findID;
        System.out.println("Введите ID товара для поиска");
        findID = sc.nextInt();
        sc.nextLine();
        String info = productService.getProduct(findID).toString();
        System.out.println(info);
    }
}
