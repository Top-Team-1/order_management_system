package org.topteam1.model;

import java.util.Map;

public enum Discount {
    NONE(0),
    REGULAR(5),
    VIP(10);
    private final int discount;

    Discount(int discountFactor) {
        this.discount = discountFactor;
    }

    public int getDiscount() {
        return discount;
    }

    /**
     * Метод для получения скидки
     *
     * @param customerType Тип покупателя
     * @return Возвращает значение Discount
     */
    public static Discount getDiscountValue(CustomerType customerType) {
        Map<CustomerType, Discount> discountMap = Map.of(
                CustomerType.VIP, Discount.VIP,
                CustomerType.REGULAR, Discount.REGULAR);
        return discountMap.getOrDefault(customerType, Discount.NONE);
    }
}
