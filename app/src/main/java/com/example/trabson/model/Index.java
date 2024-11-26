package com.example.trabson.model;

public class Index {

    private String stock;

    private String name;

    public Index() {
    }

    public Index(String stock, String name) {
        this.stock = stock;
        this.name = name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
