package org.topteam1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.OrderStatus;
import org.topteam1.model.Product;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.OrderRepository;
import org.topteam1.repository.ProductRepository;

import java.util.List;

public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    public OrderService(CustomerService customerService, ProductService productService,
                        OrderRepository orderRepository, CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
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
        customer = customerService.checkCustomerType(customerRepository.find(customer.getId()));
        log.info("Заказ успешно создан");
        return orderRepository.save(new Order(null, customer, product));
    }

    /**
     * Метод для получения заказа по ID
     *
     * @param id ID Заказа
     * @return Заказ из репозитория по ID
     */
    public Order getOrder(int id) {
        log.info("Получение заказа с ID {}", id);
        return orderRepository.find(id);
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
        Order order = orderRepository.find(id);
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
        return orderRepository.findAll();
    }

}

