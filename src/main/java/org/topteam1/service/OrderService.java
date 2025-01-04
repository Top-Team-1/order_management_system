package org.topteam1.service;

import org.topteam1.model.Customer;
import org.topteam1.model.Order;
import org.topteam1.model.OrderStatus;
import org.topteam1.model.Product;
import org.topteam1.repository.CustomerRepository;
import org.topteam1.repository.OrderRepository;
import org.topteam1.repository.ProductRepository;

import java.util.List;

public class OrderService {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    public OrderService(CustomerService customerService, OrderRepository orderRepository, CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.customerService = customerService;
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
        Customer newCustomer = customerRepository.find(customer.getId());
        newCustomer = customerService.checkCustomerType(newCustomer);
        Order newOrder = new Order(null, newCustomer, product);
        return orderRepository.save(newOrder);
    }

    /**
     * Метод для получения заказа по ID
     *
     * @param id ID Заказа
     * @return Заказ из репозитория по ID
     */
    public Order getOrder(int id) {
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
        Order order = orderRepository.find(id);
        OrderStatus orderStatus = OrderStatus.getOrderStatus(status);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    /**
     * Метод получения списка заказов.
     *
     * @return Возвращает список заказов.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}

