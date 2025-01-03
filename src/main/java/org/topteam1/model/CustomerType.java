package org.topteam1.model;

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
}
