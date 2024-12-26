package org.topteam1.model;

import java.util.Arrays;

public enum CustomerType {

    NEW("Новый покупатель"),
    REGULAR("Постоянный покупатель"),
    VIP("VIP покупатель");
    private final String rus;

    CustomerType(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    public static CustomerType getCustomerByType(int choice) {
        return Arrays.stream(values()).filter(c -> c.ordinal() + 1 == choice).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Такой категории нет"));
    }
}
