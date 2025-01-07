package org.topteam1.model;

import java.util.Objects;

public class Customer {

    private Long id;
    private String name;
    private CustomerType customerType;
    private Integer countOrder;


    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
        this.customerType = CustomerType.NEW;
        this.countOrder = 0;
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

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Integer getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(Integer countOrder) {
        this.countOrder = countOrder;
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
        return  id + "," + name + "," + customerType.getRus() + "," + countOrder;
    }

    public Customer(String customerFromFile) {
        String[] parts = customerFromFile.split(",");
        this.id = Long.parseLong(parts[0]);
        this.name = parts[1];
        this.customerType = CustomerType.getEnumValue(parts[2]);
        this.countOrder = Integer.parseInt(parts[3]);
    }
}
