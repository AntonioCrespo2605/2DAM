package com.example.erp.dataBaseObjects;

import android.graphics.Bitmap;

public class Supplier {
    private int id;
    private String name;
    private String tel;
    private String address;
    private Bitmap logo;

    public Supplier(int id, String name, String tel, String address, Bitmap logo) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.logo=logo;
    }

    public Supplier(){
        this.id=-10;
    }

    public Supplier(Supplier supplier){
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.tel = supplier.getTel();
        this.address = supplier.getAddress();
        this.logo=supplier.getLogo();
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }
}
