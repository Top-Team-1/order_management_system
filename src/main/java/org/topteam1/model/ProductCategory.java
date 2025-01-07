package org.topteam1.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static ProductCategory getEnumValue(String rusValue){
        Map<String, ProductCategory> types = new HashMap<>();
        for (ProductCategory el : ProductCategory.values()){
            types.put(el.getRus(), el);
        }
        return types.get(rusValue);
    }
}
