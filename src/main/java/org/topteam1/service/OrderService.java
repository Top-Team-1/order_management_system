package org.topteam1.service;

import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.Product;
import org.topteam1.repository.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
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
        Order newOrder = new Order(null, customer, product);
        return orderRepository.save(newOrder);
    }

    /**
     * Метод получения списка заказов.
     *
     * @return Возвращает список заказов.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findOrder();
    }
}
