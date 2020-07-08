package com.example.shopkeeper.Model;

public class Users {
    String username;
    String email;
    String mobile;
    boolean customer;
    boolean shopkeeper;
    String id;

    public Users(String username, String email, String mobile, boolean customer, boolean shopkeeper,String id) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.customer = customer;
        this.shopkeeper = shopkeeper;
        this.id = id;
    }

    public Users() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public boolean isShopkeeper() {
        return shopkeeper;
    }

    public void setShopkeeper(boolean shopkeeper) {
        this.shopkeeper = shopkeeper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
