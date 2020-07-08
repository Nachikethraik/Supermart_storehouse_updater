package com.example.shopkeeper.Model;

public class itemlist {
    String id;
    String itemname;

    public itemlist(String id, String itemname) {
        this.id = id;
        this.itemname = itemname;
    }

    public itemlist() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
