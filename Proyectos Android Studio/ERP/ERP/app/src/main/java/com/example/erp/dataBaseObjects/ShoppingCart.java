package com.example.erp.dataBaseObjects;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Integer>cants;
    private ArrayList<Product>products;

    public ShoppingCart() {
        this.cants = new ArrayList<Integer>();
        this.products = new ArrayList<Product>();
    }

    public ArrayList<Integer> getCants() {
        return cants;
    }

    public void setCants(ArrayList<Integer> cants) {
        this.cants = cants;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product, int cant){
        products.add(product);
        cants.add(cant);
    }
}
