package org.topteam1.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    NEW("Новый заказ"),
    PROCESSING("Заказ обрабатывается"),
    COMPLETED("Заказ завершён"),
    CANCELED("Заказ отменён");
    final private String rus;

    private OrderStatus(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    public static OrderStatus getOrderStatus(int choice) {
        return Arrays.stream(values())
                .filter(s -> s.ordinal() == choice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Такого статуса нет"));
    }

    /**
     * Метод определяет корректное значение Enum
     *
     * @param rusValue Русское название
     * @return корректное значение Enum
     */

    public static OrderStatus getEnumValue(String rusValue) {
        Map<String, OrderStatus> types = new HashMap<>();
        for (OrderStatus el : OrderStatus.values()) {
            types.put(el.getRus(), el);
        }
        return types.get(rusValue);
    }
}
