/**
 * Класс репозиторий для хранения товара.
 */
package org.topteam1.repository;

import org.topteam1.Exceptions.ProductFileNotFoundException;
import org.topteam1.Exceptions.ProductNotAddException;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class ProductRepository {
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

        try {
            Files.write(filePath, (product + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            Files.write(filePathId, id.toString().getBytes());
        } catch (IOException e) {
            throw new ProductNotAddException("Не удалось добавить товар!");
        }
        return product;
    }

    /**
     * Метод возвращает список всех товаров.
     *
     * @return список товаров.
     */
    public List<Product> findAll() {
        try {
           return Files.readAllLines(filePath).stream()
                    .map(Product::new)
                    .toList();
        } catch (IOException e) {
            throw new ProductFileNotFoundException("Файл записи не найден!");
        }
    }

    /**
     * Метод возвращает товар по ID.
     *
     * @param id id товара.
     * @return найденный товар.
     */
    public Product find(int id) {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.map(Product::new)
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new ProductNotFoundException("Товара с ID " + id + " не найден!"));
        } catch (IOException e) {
            throw new ProductFileNotFoundException("Файл записи не найден!");
        }
    }
}
