package org.topteam1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.OrderNotFoundException;
import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.OrderStatus;
import org.topteam1.model.Product;
import org.topteam1.repository.OrderRepository;

import java.util.List;

public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public OrderService(CustomerService customerService, ProductService productService,
                        OrderRepository orderRepository) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    /**
     * Метод добавления заказа в систему
     *
     * @param customer Покупатель, который делает заказ.
     * @param product  Товар данного заказа.
     * @return Возвращает созданный заказ.
     */
    public Order addOrder(Customer customer, Product product) {
        log.info("Добавление нового заказа. Покупатель ID: {}, Товар ID: {}", customer.getId(), product.getId());
        productService.calculateDiscount(product, customer);
        customer = customerService.checkCustomerType(customerService.getCustomerById(customer.getId()));
        log.info("Заказ успешно создан");
        return orderRepository.save(new Order(null, customer, product));
    }

    /**
     * Метод для получения заказа по ID
     *
     * @param id ID Заказа
     * @return Заказ из репозитория по ID
     */
    public Order getOrderById(int id) {
        log.info("Получение заказа с ID {}", id);
        return getAllOrders().stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Заказ с ID " + id + " не найден!"));
    }

    /**
     * Метод для обновления статуса заказа
     *
     * @param id     ID заказа
     * @param status Выбираемый статус заказа
     * @return Метод возвращает обновленный статус заказа
     */
    public Order updateOrderStatus(int id, int status) {
        log.info("Обновление статуса заказа. ID заказа: {}, Новый статус: {}", id, status);
        Order order = getOrderById(id);
        log.info("Текущий заказ перед обновлением статуса: {}", order);

        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELED) {
            log.warn("Попытка изменить статус, недоступный для изменения");
            return order;
        }

        order.setOrderStatus(OrderStatus.getOrderStatus(status));
        log.info("Статус заказа успешно обновлен");
        return orderRepository.save(order);
    }

    /**
     * Метод получения списка заказов.
     *
     * @return Возвращает список заказов.
     */
    public List<Order> getAllOrders() {
        log.info("Получение списка всех заказов");
        return orderRepository.findAll().stream()
                .map(Order::new)
                .toList();
    }
}

