package com.example.trabson.model;

import java.util.ArrayList;

public class Wallet {

    private int id;

    private double budget, profitability;

    private ArrayList<StockExchange> stocks;

    public Wallet() { stocks = new ArrayList<>(); }

    public Wallet(int id, double budget, double profitability, ArrayList<StockExchange> stocks) {
        this.id = id;
        this.budget = budget;
        this.profitability = profitability;
        this.stocks = stocks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public ArrayList<StockExchange> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<StockExchange> stocks) {
        this.stocks = stocks;
    }

    public void addStock(StockExchange stock) {
        stocks.add(stock);
    }
}
