package org.topteam1.repository;

import org.topteam1.Exceptions.OrderNotFoundException;
import org.topteam1.model.Customer;
import org.topteam1.model.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class OrderRepository {

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
        try {
            List<String> orders = Files.readAllLines(filePath);
            if (order.getId() == null) {
                order.setId(++id);
                Files.write(filePath, (order + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                Files.write(filePathId, id.toString().getBytes());
            } else {
                List<String> updateOrderstatus = orders.stream()
                        .map(o -> {
                            Order newOrder = new Order(o);
                            if (newOrder.getId().equals(order.getId())) {
                                return order.toString();
                            }
                            return o;
                        })
                        .toList();
                Files.write(filePath, updateOrderstatus);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    /**
     * Метод для поиска заказа по ID
     *
     * @param id В качестве параметра принимает ID заказа.
     * @return Возвращает заказ найденный по ID.
     */
    public Order find(int id) {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.map(Order::new)
                    .filter(o -> o.getId() == id)
                    .findFirst().orElseThrow(() -> new OrderNotFoundException("Заказ с ID " + id + " не найден!"));
        } catch (IOException e) {
            throw new OrderNotFoundException(e.getMessage()); // заглушка
        }
    }

    /**
     * Метод поиска списка заказов
     *
     * @return Список заказов
     */
    public List<Order> findAll() {
        try {
            return Files.readAllLines(filePath).stream()
                    .map(Order::new)
                    .toList();
        } catch (IOException e) {
            throw new OrderNotFoundException(e.getMessage()); // заглушка. поменять
        }
    }

    /**
     * Метод сохранения статуса заказа
     *
     * @param order Заказ
     * @return Возвращает заказ с обновленным статусом
     */
    public Order saveNewOrderStatus(Order order) {
        try {
            List<String> allOrders = Files.readAllLines(filePath);
            List<String> updateStatus = allOrders.stream()
                    .map(o -> {
                        Order orderWithNewStatus = new Order(o);
                        if (orderWithNewStatus.getId().equals(order.getId())) {
                            return order.toString();
                        }
                        return o;
                    })
                    .toList();
            Files.write(filePath, updateStatus);
        } catch (IOException e) {
            System.out.println(e.getMessage()); //заглушка
        }
        return order;
    }
}
