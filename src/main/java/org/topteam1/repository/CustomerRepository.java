package org.topteam1.repository;

import org.topteam1.Exceptions.CustomerFileNotFoundException;
import org.topteam1.Exceptions.CustomerNotAddException;
import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class CustomerRepository {

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
    public Customer saveCustomer(Customer customer) {

        customer.setId(++id);

        try {
            //Files.write(filePath, customer.toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(filePath, (customer.toString() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            Files.write(filePathId, id.toString().getBytes());
        } catch (IOException e) {
            throw new CustomerNotAddException("Не удалось добавить покупателя!");
        }
        return customer;
    }

    /**
     * Метод для отображения списка покупателей
     *
     * @return возвращает список всех покупателей
     */
    public List<Customer> findAllCustomers() {

        try {
            return Files.readAllLines(filePath)
                    .stream()
                    .map(Customer::new)
                    .toList();
        } catch (IOException e) {
            throw new CustomerFileNotFoundException("Файл записи не найден!");
        }
    }

    /**
     * Метод для поиска покупателя по ID
     *
     * @param id принимает в себя ID покупателя
     * @return возвращает покупателя с заданным ID
     */
    public Customer findCustomer(int id) {

        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.map(Customer::new)
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new CustomerNotFoundException("Покупатель с ID " + id + " не найден!"));
        } catch (IOException e) {
            throw new CustomerFileNotFoundException("Файл записи не найден!");
        }
    }
}
