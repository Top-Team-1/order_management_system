package org.topteam1.model;

import java.util.Arrays;

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
}
