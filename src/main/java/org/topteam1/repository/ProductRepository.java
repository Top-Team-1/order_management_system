/**
 * Класс репозиторий для хранения товара.
 */
package org.topteam1.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.ProductFileNotFoundException;
import org.topteam1.Exceptions.ProductNotAddException;
import org.topteam1.model.Product;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class ProductRepository {
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    private final Path filePath;
    private final Path filePathId;
    private Long id;


    public ProductRepository(String file) {
        this.filePath = Path.of(file);
        this.filePathId = Path.of(file + "_id");
        id = 0L;

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            if (Files.exists(filePathId)) {
                id = Long.parseLong(Files.readString(filePathId));
            } else {
                Files.createFile(filePathId);
                Files.write(filePathId, id.toString().getBytes());
            }
        } catch (IOException e) {
            System.out.println("Ошибка - " + e);
        }
    }

    /**
     * Метод сохраняет товар в репозиторий.
     *
     * @param product товар для сохранения.
     * @return сохранённый товар.
     */
    public Product save(Product product) {
        product.setId(++id);
        log.info("Сохраняется товар: {}", product);

        try {
            Files.write(filePath, (product + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            Files.write(filePathId, id.toString().getBytes());
            log.info("Товар сохранён: {}", product);
        } catch (IOException e) {
            log.error("Ошибка сохранения товара: ", e);
            throw new ProductNotAddException("Не удалось добавить товар!");
        }
        return product;
    }

    /**
     * Метод возвращает список всех товаров.
     *
     * @return список товаров.
     */
    public List<String> findAll() {
        log.info("Получение товаров из файла: {}", filePath);
        try {
            return Files.readAllLines(filePath).stream()
                    .toList();
        } catch (IOException e) {
            log.error("Ошибка получения товаров из файла: ", e);
            throw new ProductFileNotFoundException("Файл записи не найден!");
        }
    }
}
