package com.example.shopkeeper.Model;

public class Bill {
    String BillNumber;
    String CustomerName;
    String NoofItems;
    String TotalAmount;

    public Bill(String BillNumber, String CustomerName, String NoofItems, String TotalAmount) {
        this.BillNumber = BillNumber;
        this.CustomerName = CustomerName;
        this.NoofItems = NoofItems;
        this.TotalAmount = TotalAmount;
    }

    public Bill() {
    }

    public String getBillNumber() {
        return BillNumber;
    }

    public void setBillNumber(String BillNumber) {
        this.BillNumber = BillNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getNoofItems() {
        return NoofItems;
    }

    public void setNoofItems(String NoofItems) {
        this.NoofItems = NoofItems;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String TotalAmount) {
        this.TotalAmount = TotalAmount;
    }
}
