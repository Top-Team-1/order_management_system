/**
 * Класс сервис для работы с товарами.
 */
package org.topteam1.service;

import org.topteam1.model.Product;
import org.topteam1.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Метод добавляет новый товар в систему. Информацию получаем из метода контроллера.
     *
     * @param name     Наименование товара.
     * @param price    Цена товара.
     * @param category Категория товара.
     * @return Возвращает созданный объект.
     */
    public Product addProduct(String name, Integer price, String category) {
        Product newProduct = new Product(null, name, price, category);
        return productRepository.save(newProduct);
    }

    /**
     * Метод возвращает список всех товаров.
     *
     * @return список товаров.
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Метод возвращает товар, найденный по ID.
     *
     * @param id id товара
     * @return возвращает товар по id.
     */
    public Product getProduct(int id) {
        return productRepository.returnProduct(id);
    }
}
