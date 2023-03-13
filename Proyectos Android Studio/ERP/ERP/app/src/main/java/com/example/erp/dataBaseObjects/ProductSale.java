package com.example.erp.dataBaseObjects;

public class ProductSale {
    private int amount;
    private double indPrice;
    private Product product;

    public ProductSale(Product product, int amount, double indPrice){
        this.amount = amount;
        this.indPrice = indPrice;
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getIndPrice() {
        return indPrice;
    }

    public void setIndPrice(double indPrice) {
        this.indPrice = indPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
