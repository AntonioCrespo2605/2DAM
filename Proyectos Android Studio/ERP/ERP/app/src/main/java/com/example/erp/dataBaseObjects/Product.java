package com.example.erp.dataBaseObjects;

import android.graphics.Bitmap;

public class Product {
    private int id;
    private String name;
    private String description;
    private int stock;
    private double current_price;
    private Bitmap photo;

    public Product(){

    }

    public Product(int id, String name, String description, int stock, double current_price, Bitmap photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.current_price = current_price;
        this.photo=photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
