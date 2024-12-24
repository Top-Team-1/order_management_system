package org.topteam1.model;

import java.util.Arrays;

public enum ProductCategory {
    /**
     * НЕ МЕНЯТЬ ПОРЯДОК. Любое изменение влияет на присвоение категории товару!
     */
    SMARTPHONE("Смартфоны"),
    TV("Телевизоры"),
    HOUSEHOLD_APPLIANCES("Бытовая техника");
    private final String rus;

    ProductCategory(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    public static ProductCategory getProductByNumber(int choice) {
        return Arrays.stream(values())
                .filter(c -> c.ordinal() + 1 == choice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Такой категории нет"));
    }
}
