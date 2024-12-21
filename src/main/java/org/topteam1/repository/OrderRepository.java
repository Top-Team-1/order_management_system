package org.topteam1.repository;

import org.topteam1.Exceptions.OrderNotAddException;
import org.topteam1.Exceptions.OrderNotFoundException;
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
        throw new OrderNotAddException("Не получилось добавить заказ");
    }

    /**
     * Метод для поиска товара по ID.
     * @param id В качестве параметра принимает ID заказа.
     * @return Возвращает товар найденный по ID.
     */
    public Order findOrder(int id){
        return orderList.stream()
                .filter(o -> o != null && o.getId() == id)
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Заказ с ID " + id + " не найден!"));
    }

    /**
     * Метод поиска списка заказов
     *
     * @return Список заказов
     */
    public List<Order> findAllOrder() {
        return orderList;
    }
}
