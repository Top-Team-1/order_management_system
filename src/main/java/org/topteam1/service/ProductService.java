/**
 * Класс сервис для работы с товарами.
 */
package org.topteam1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.model.*;
import org.topteam1.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

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
    public Product addProduct(String name, Integer price, ProductCategory category) {
        log.info("Попытка добавить товар: name={}, price={}, category={}", name, price, category);
        Product newProduct = new Product(name, price, category);
        log.info("Товар успешно добавлен: {}", newProduct.getName() + " " + newProduct.getPrice() + " " + newProduct.getCategory());
        return productRepository.save(newProduct);
    }

    /**
     * Метод возвращает список всех товаров.
     *
     * @return список товаров.
     */
    public List<Product> getAll() {
        log.info("Поиск всех товаров");
        return productRepository.findAll();
    }

    /**
     * Метод возвращает товар, найденный по ID.
     *
     * @param id id товара
     * @return возвращает товар по id.
     */
    public Product getProduct(int id) {
        log.info("Идёт поиск товара по id: {}", id);
        return productRepository.find(id);
    }

    /**
     * Метод для расчёта скидки покупателя, в зависимости от его статуса.
     * @param product Товар
     * @param customer Покупатель
     */
    public void calculateDiscount(Product product, Customer customer) {
        if (customer.getCustomerType() == CustomerType.VIP){
            product.setPrice(product.getPrice() - (product.getPrice()) * Discount.VIP.getDiscount() / 100);
        }else if(customer.getCustomerType() == CustomerType.REGULAR){
            product.setPrice(product.getPrice() - (product.getPrice()) * Discount.REGULAR.getDiscount() / 100);
        }
    }
}
