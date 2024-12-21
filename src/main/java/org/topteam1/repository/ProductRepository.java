/**
 * Класс репозиторий для хранения товара.
 */
package org.topteam1.repository;

import org.topteam1.Exceptions.ProductNotAddException;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final List<Product> products;
    private int countID;

    public ProductRepository() {
        this.products = new ArrayList<>();
        countID = 0;
    }

    /**
     * Метод сохраняет товар в репозиторий.
     *
     * @param product товар для сохранения.
     * @return сохранённый товар.
     */
    public Product save(Product product) {
        product.setId(++countID);

        if (products.add(product)) {
            return product;
        }
        throw new ProductNotAddException("Не получилось добавить товар");
    }

    /**
     * Метод возвращает список всех товаров.
     *
     * @return список товаров.
     */
    public List<Product> findAll() {
        return products;
    }

    /**
     * Метод возвращает товар по ID.
     *
     * @param id id товара.
     * @return найденный товар.
     */
    public Product returnProduct(int id) {
        return products.stream()
                .filter(p -> p != null && p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Товара с ID " + id + " не найден!"));
    }
}
