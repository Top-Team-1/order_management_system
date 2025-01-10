package org.topteam1.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.CustomerFileNotFoundException;
import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class CustomerRepository {
    private static final Logger log = LoggerFactory.getLogger(CustomerRepository.class);

    private final Path filePath;
    private final Path filePathId;
    private Long id;

    public CustomerRepository(String file) {

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
     * Метод для добавления покупателя
     *
     * @param customer содержит данные о покупателе
     * @return возвращает покупателя
     */
    public Customer save(Customer customer) {
        log.info("Сохраняется покупатель: {}", customer);

        try {
            List<String> customers = Files.readAllLines(filePath);
            if (customer.getId() == null) {
                customer.setId(++id);
                Files.write(filePath, (customer + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                Files.write(filePathId, id.toString().getBytes());
                log.info("Покупатель сохранен: {}", customer);
            } else {
                List<String> updateCustomerStatus = customers.stream()
                        .map(c -> {
                            Customer newCustomer = new Customer(c);
                            if (newCustomer.getId().equals(customer.getId())) {
                                return customer.toString();
                            }
                            return c;
                        })
                        .toList();
                Files.write(filePath, updateCustomerStatus);
            }
        } catch (IOException e) {
            log.error("Ошибка сохранения покупателя: ", e);
            System.out.println(e.getMessage());
        }
        return customer;
    }

    /**
     * Метод для отображения списка покупателей
     *
     * @return возвращает список всех покупателей
     */
    public List<String> findAll() {

        log.info("Получение покупателей из файла: {}", filePath);

        try {
            return Files.readAllLines(filePath).stream()
                    .toList();
        } catch (IOException e) {
            log.error("Ошибка получения покупателей из файла ", e);
            throw new CustomerFileNotFoundException("Файл записи не найден!");
        }
    }
}
