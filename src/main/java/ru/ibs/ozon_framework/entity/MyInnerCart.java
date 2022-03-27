package ru.ibs.ozon_framework.entity;

import java.util.ArrayList;
import java.util.List;

public class MyInnerCart {
    private static MyInnerCart myInnerCart = null;
    private String name = "my cart";
    private List<Product> cartInnerlist = new ArrayList<>();

    private MyInnerCart() {

    }

    public static MyInnerCart getInstance() {
        if (myInnerCart == null) {
            myInnerCart = new MyInnerCart();
        }
        return myInnerCart;
    }

    public String getName() {
        return name;
    }

    public void loadInnerCart(Product product) {
        cartInnerlist.add(product);
    }

    public List<Product> getCartInnerlist() {
        return cartInnerlist;
    }

    @Override
    public String toString() {
        return "MyInnerCart{" +
                "name='" + name + '\'' +
                ", cartInnerlist=" + cartInnerlist +
                '}';
    }
}
