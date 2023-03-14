package com.example.erp.dataBaseObjects;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Integer>amounts;
    private ArrayList<Product>products;

    public ShoppingCart() {
        this.amounts = new ArrayList<Integer>();
        this.products = new ArrayList<Product>();
    }

    public ArrayList<Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(ArrayList<Integer> amounts) {
        this.amounts = amounts;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product, int amount){
        products.add(product);
        amounts.add(amount);
    }
}
