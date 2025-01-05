package org.topteam1.model;

import java.util.Objects;

public class Order {
    private Long id;
    private Customer customer;
    private Product product;
    private OrderStatus orderStatus;

    public Order(Long id, Customer customer, Product product) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.orderStatus = OrderStatus.NEW;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(product, order.product) && Objects.equals(orderStatus, order.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, product, orderStatus);
    }

    @Override
    public String toString() {
        return id + "/" + customer + "/" + product + "/" + orderStatus.getRus();
    }

    public Order(String orderFromFile) {
        String[] parts = orderFromFile.split("/");
        this.id = Long.parseLong(parts[0]);
        this.customer = new Customer(parts[1]);
        this.product = new Product(parts[2]);
        this.orderStatus = OrderStatus.getEnumValue(parts[3]);
    }
}
