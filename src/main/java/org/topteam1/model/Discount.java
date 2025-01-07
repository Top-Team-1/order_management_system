package org.topteam1.model;

public enum Discount {
    REGULAR(5),
    VIP(10);
    private final int discount;

    Discount(int discountFactor) {
        this.discount = discountFactor;
    }

    public int getDiscount() {
        return discount;
    }
}
