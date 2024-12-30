/**
 * Класс описывающий товар.
 */
package org.topteam1.model;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private String category;

    public Product(String name, Integer price, String category) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.category = category;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category);
    }

    @Override
    public String toString() {
        return id + "," + name + "," + price + "," + category;
    }

    public Product(String productFromFile) {
        String[] parts = productFromFile.split(",");
        this.id = Long.parseLong(parts[0]);
        this.name = parts[1];
        this.price = Integer.parseInt(parts[2]);
        this.category = parts[3];
    }
}
