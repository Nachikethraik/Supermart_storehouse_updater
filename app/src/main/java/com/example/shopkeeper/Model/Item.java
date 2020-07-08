package com.example.shopkeeper.Model;

public class Item {
    String id;
    String Name;
    String search;
    String MRP;
    String Discount;
    String finalprice;
    String Quantity;

    public Item(String id, String itemname, String search, String mrp, String discount, String finalprice, String quantity) {
        this.id = id;
        this.Name = itemname;
        this.search = search;
        this.MRP = mrp;
        this.Discount = discount;
        this.finalprice = finalprice;
        this.Quantity = quantity;
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        this.Discount = discount;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(String finalprice) {
        this.finalprice = finalprice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        this.Quantity = quantity;
    }
}
