package org.topteam1.model;

import java.util.HashMap;
import java.util.Map;

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

    public static CustomerType getEnumValue(String rusValue){
        Map<String, CustomerType> types = new HashMap<>();
        for(CustomerType el : CustomerType.values()){
            types.put(el.getRus(), el);
        }
        return types.get(rusValue);
    }
}
