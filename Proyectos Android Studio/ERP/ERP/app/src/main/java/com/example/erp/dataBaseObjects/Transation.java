package com.example.erp.dataBaseObjects;

public class Transation {
    private int id;
    private String date;
    private String reason;
    private double amount;

    public Transation(int id, String date, String reason, double amount) {
        this.id = id;
        this.date = date;
        this.reason = reason;
        this.amount=amount;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
