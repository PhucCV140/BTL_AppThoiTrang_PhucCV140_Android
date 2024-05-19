package com.example.appthoitrang.Domain;

import java.util.ArrayList;

public class PurchasedDomain {
    private String username, title, date;
    private ArrayList<String> picUrl;
    private double price;
    private int quantity;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PurchasedDomain() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PurchasedDomain(String username, String title, String date, ArrayList<String> picUrl, double price, int quantity) {
        this.username = username;
        this.title = title;
        this.date = date;
        this.picUrl = picUrl;
        this.price = price;
        this.quantity = quantity;
    }
}
