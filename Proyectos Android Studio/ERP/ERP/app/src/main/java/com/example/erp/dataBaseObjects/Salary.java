package com.example.erp.dataBaseObjects;

public class Salary {
    private double salary;
    private String date;

    public Salary(double salary, String date) {
        this.salary = salary;
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
