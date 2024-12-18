package org.topteam1.model;

import java.util.Objects;

public class Customer {

    private Integer id;
    private String name;
    private String customerType;

    public Customer(Integer id, String name, String customerType) {
        this.id = id;
        this.name = name;
        this.customerType = customerType;
    }

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(customerType, customer.customerType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customerType);
    }

    @Override
    public String toString() {
        return "ID покупателя: " + id + ". Имя покупателя: " + name + ". Тип покупателя: " + customerType;
    }
}
