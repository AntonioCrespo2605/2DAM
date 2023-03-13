package com.example.erp.dataBaseObjects;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Customer {
    private int id;
    private String name;
    private String tel;
    private String email;
    private ShoppingCart shoppingCart;
    private Bitmap photo;
    private ArrayList<Message>mailbox;
    private String password;

    public Customer(int id, String name, String tel, String email, ShoppingCart shoppingCart, Bitmap photo, ArrayList<Message>mailbox,String password) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.shoppingCart=shoppingCart;
        this.photo=photo;
        this.mailbox=mailbox;
        this.password=password;
    }

    public Customer(int id, String name, String tel, String email, Bitmap photo, String password) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.shoppingCart=new ShoppingCart();
        this.photo=photo;
        this.mailbox=new ArrayList<Message>();
        this.password=password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<Message> getMailbox() {
        return mailbox;
    }

    public void setMailbox(ArrayList<Message> mailbox) {
        this.mailbox = mailbox;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addMessage(Message message){
        this.mailbox.add(message);
    }
}
