/**
 * Класс контроллер для обработки запросов из консоли.
 */
package org.topteam1.controller;

import org.topteam1.Exceptions.ProductNotAddException;
import org.topteam1.Exceptions.ProductNotFoundException;
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
            System.out.println(">>>>Управление товарами<<<<\n" +
                    "1) Добавить товар\n" +
                    "2) Посмотреть все доступные товары\n" +
                    "3) Найти товар по ID\n" +
                    "0) Назад");
            choise = sc.nextInt();
            sc.nextLine();
            switch (choise) {
                case 1 -> addProduct();
                case 2 -> getProductList();
                case 3 -> findProduct();
                case 0 -> {
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
        int categoryNumber;
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
            default -> {
                System.out.println("Такой категории нет, попробуйте ещё раз");
                return;
            }
        }
        try {
            String info = productService.addProduct(productName, productPrice, productCategory).toString();
            System.out.println(info);
        }catch (ProductNotAddException e){
            System.out.println(e.getMessage());
        }
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
        try {
            String info = productService.getProduct(findID).toString();
            System.out.println(info);
        }catch (ProductNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
