package org.topteam1.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.OrderFileNotFoundException;
import org.topteam1.model.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private final Path filePath;
    private final Path filePathId;
    private Long id;


    public OrderRepository(String file) {
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод сохраняет заказ в репозиторий
     *
     * @param order Заказ для сохранения
     * @return Сохранённый заказ
     */
    public Order save(Order order) {
        log.info("Сохранение нового заказа");
        try {
            List<String> orders = Files.readAllLines(filePath);
            if (order.getId() == null) {
                order.setId(++id);
                Files.write(filePath, (order + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                Files.write(filePathId, id.toString().getBytes());
                log.info("Заказ с ID {} успешно сохранен: {}", order.getId(), order);
            } else {
                List<String> updateOrderStatus = orders.stream()
                        .map(o -> {
                            Order newOrder = new Order(o);
                            if (newOrder.getId().equals(order.getId())) {
                                return order.toString();
                            }
                            return o;
                        })
                        .toList();
                Files.write(filePath, updateOrderStatus);
            }
        } catch (IOException e) {
            log.error("Ошибка при сохранении заказа: {}", e.getMessage());
            System.out.println(e.getMessage());
        }
        return order;
    }

    /**
     * Метод поиска списка заказов
     *
     * @return Список заказов
     */
    public List<String> findAll() {
        log.info("Получение всех заказов из файла {}", filePath);
        try {
            return Files.readAllLines(filePath).stream()
                    .toList();
        } catch (IOException e) {
            throw new OrderFileNotFoundException("Файл записи не найден!");
        }
    }
}
