package com.example.erp.dataBaseObjects;

import java.util.ArrayList;

public class Supply {
    private int id;
    private Supplier supplier;
    private String date;
    private boolean state;//true para entregado
    private double shipping_costs;
    private ArrayList<ProductSale>lines;

    public Supply(int id, Supplier supplier, String date, boolean state, double shipping_costs, ArrayList<ProductSale> lines) {
        this.id = id;
        this.supplier = supplier;
        this.date = date;
        this.state = state;
        this.shipping_costs = shipping_costs;
        this.lines = lines;
    }

    public Supply(int id, Supplier supplier, String date, boolean state, double shipping_costs){
        this.id = id;
        this.supplier = supplier;
        this.date = date;
        this.state = state;
        this.shipping_costs = shipping_costs;
        this.lines = new ArrayList<ProductSale>();
    }

    public Supply(Supply supply){
        this.id = supply.getId();
        this.supplier = supply.getSupplier();
        this.date = supply.getDate();
        this.state = supply.isState();
        this.shipping_costs = supply.getShipping_costs();
        this.lines = supply.getLines();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getShipping_costs() {
        return shipping_costs;
    }

    public void setShipping_costs(double shipping_costs) {
        this.shipping_costs = shipping_costs;
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
}
