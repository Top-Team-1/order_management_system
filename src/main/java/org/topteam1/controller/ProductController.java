/**
 * Класс контроллер для обработки запросов из консоли.
 */
package org.topteam1.controller;

import org.topteam1.Exceptions.ProductFileNotFoundException;
import org.topteam1.Exceptions.ProductNotAddException;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.ProductCategory;
import org.topteam1.service.ProductService;

import java.util.Scanner;

public class ProductController {
    private final ProductService productService;

    Scanner sc = new Scanner(System.in);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Метод запускает взаимодействие с сущностью "Товар".
     */
    public void start() {
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
            switch (choice) {
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
        String productName = sc.nextLine();
        System.out.print("Введите цену товара - ");
        Integer productPrice = sc.nextInt();
        sc.nextLine();
        System.out.println("Выберете категорию товара\n" +
                "1)" + ProductCategory.SMARTPHONE.getRus() +"\n" +
                "2)" + ProductCategory.TV.getRus() + "\n" +
                "3)" + ProductCategory.HOUSEHOLD_APPLIANCES.getRus());
        categoryNumber = sc.nextInt();
        try {
            String productCategory = String.valueOf(ProductCategory.getProductByNumber(categoryNumber).getRus());
            String info = productService.addProduct(productName, productPrice, productCategory).toString();
            System.out.println(info);
        } catch (IllegalArgumentException | ProductNotAddException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод отображает все доступные товары.
     */
    private void getProductList() {
        try {
            String info = productService.getAll().toString();
            System.out.println(info);
        }catch (ProductFileNotFoundException e){
            System.out.println(e.getMessage());
        }
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
        } catch (ProductFileNotFoundException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
