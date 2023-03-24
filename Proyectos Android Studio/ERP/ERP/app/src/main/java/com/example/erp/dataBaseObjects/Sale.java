package com.example.erp.dataBaseObjects;

import java.util.ArrayList;

public class Sale {
    private int id;
    private String date;
    private double shipping_costs;
    private boolean state;
    private Employee seller;
    private Customer buyer;
    private ArrayList <ProductSale> lines;

    public Sale(int id, String date, double shipping_costs, boolean state, Employee seller, Customer buyer, ArrayList<ProductSale> lines) {
        this.id = id;
        this.date = date;
        this.shipping_costs = shipping_costs;
        this.state = state;
        this.seller = seller;
        this.buyer = buyer;
        this.lines = lines;
    }

    public Sale(int id, String date, double shipping_costs, boolean state, Employee seller, Customer buyer) {
        this.id = id;
        this.date = date;
        this.shipping_costs = shipping_costs;
        this.state = state;
        this.seller = seller;
        this.buyer = buyer;
        this.lines = new ArrayList<ProductSale>();
    }

    public Sale(Sale sale){
        this.id = sale.getId();
        this.date = sale.getDate();
        this.shipping_costs = sale.shipping_costs;
        this.state = sale.isState();
        this.seller = sale.getSeller();
        this.buyer = sale.getBuyer();
        this.lines = sale.getLines();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getShipping_costs() {
        return shipping_costs;
    }

    public void setShipping_costs(double shipping_costs) {
        this.shipping_costs = shipping_costs;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Employee getSeller() {
        return seller;
    }

    public void setSeller(Employee seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public ArrayList<ProductSale> getLines() {
        return lines;
    }

    public void setLines(ArrayList<ProductSale> lines) {
        this.lines = lines;
    }

    public void addLine(ProductSale productSale){
        this.lines.add(productSale);
    }


    //needed functions
    private double getTotalPrice(){
        double toret=0;
        for(ProductSale productSale:getLines()){
            toret+=(productSale.getAmount() * productSale.getIndPrice());
        }
        return toret;
    }
}
