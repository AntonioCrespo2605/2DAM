package com.example.erp.dataBaseObjects;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Employee {
    private int id;
    private String dni;
    private String name;
    private String tel;
    private String workstation;
    private String bank_number;
    private double current_salary;
    private ArrayList<Salary>salaries;
    private Bitmap photo;
    private String password;

    public Employee(int id, String dni, String name, String tel, String workstation, String bank_number, double current_salary, ArrayList<Salary> salaries, Bitmap photo, String password) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.tel = tel;
        this.workstation = workstation;
        this.bank_number = bank_number;
        this.current_salary = current_salary;
        this.salaries = salaries;
        this.photo=photo;
        this.password=password;
    }

    public Employee(int id, String dni, String name, String tel, String workstation, String bank_number, double current_salary, Bitmap photo, String password) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.tel = tel;
        this.workstation = workstation;
        this.bank_number = bank_number;
        this.current_salary = current_salary;
        this.salaries = new ArrayList<Salary>();
        this.photo=photo;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public double getCurrent_salary() {
        return current_salary;
    }

    public void setCurrent_salary(double current_salary) {
        this.current_salary = current_salary;
    }

    public ArrayList<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(ArrayList<Salary> salaries) {
        this.salaries = salaries;
    }

    public void addSalary(Salary salary){
        this.salaries.add(salary);
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
