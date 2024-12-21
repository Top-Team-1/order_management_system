package org.topteam1.repository;

import org.topteam1.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final List<Order> orderList;
    private int countId;


    public OrderRepository() {
        this.orderList = new ArrayList<>();
        countId = 0;
    }

    /**
     * Метод сохраняет заказ в репозиторий
     *
     * @param order Заказ для сохранения
     * @return Сохранённый заказ
     */
    public Order save(Order order) {
        order.setId(++countId);

        if (orderList.add(order)) {
            return order;
        }
        return null;
    }

    /**
     * Метод поиска списка заказов
     *
     * @return Список заказов
     */
    public List<Order> findOrder() {
        return orderList;
    }
}
