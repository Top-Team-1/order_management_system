package org.topteam1.model;

import java.util.Objects;

public class Customer {

    private Long id;
    private String name;
    private String customerType;

    public Customer(String name, String customerType) {
        this.id = null;
        this.name = name;
        this.customerType = customerType;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return  id + ", " + name + ", " + customerType + "\n";
    }

    public Customer(String customerFromFile) {
        String[] parts = customerFromFile.split(",");
        this.id = Long.parseLong(parts[0]);
        this.name = parts[1];
        this.customerType = parts[2];
    }
}
