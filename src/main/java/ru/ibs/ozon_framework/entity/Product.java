package ru.ibs.ozon_framework.entity;

import java.util.Objects;

public class Product {
    private String name;
    private Integer price;
    private Boolean biggestPrice;

    public Product() {
    }

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Boolean getBiggestPrice() {
        return biggestPrice;
    }

    public void setBiggestPrice(Boolean biggestPrice) {
        this.biggestPrice = biggestPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(biggestPrice, product.biggestPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, biggestPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", biggestPrice=" + biggestPrice +
                '}';
    }
}
