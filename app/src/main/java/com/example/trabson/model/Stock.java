package com.example.trabson.model;

public class Stock {

    private String stock, name, logo, sector, type;

    private double  close, change, market_cap;

    private long volume;

    public Stock() {
    }

    public Stock(String stock, String name, String logo, String sector, String type, double close, double change, long volume, double market_cap) {
        this.stock = stock;
        this.name = name;
        this.logo = logo;
        this.sector = sector;
        this.type = type;
        this.close = close;
        this.change = change;
        this.volume = volume;
        this.market_cap = market_cap;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }
}
