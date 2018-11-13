package com.ram.hari.android.bitsniteeats;

public class Order {
    private String items,mess,number,timestamp,userEmail;
    private int price;

    public Order(String items, String mess, String number, int price, String timestamp, String userEmail) {
        this.items = items;
        this.mess = mess;
        this.number = number;
        this.price = price;
        this.timestamp = timestamp;
        this.userEmail = userEmail;
    }

    public Order() {
        items="";
        price=0;
        mess="";
        timestamp="";
        userEmail="";
        number="";
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
