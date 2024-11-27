package com.example.trabson.model;

import com.example.trabson.model.Enum.ETypeStock;
import com.example.trabson.model.Enum.ETypeTransaction;

import java.util.Date;

public class UserStock {

    private int id, quantity;

    private String name, code;

    private ETypeStock stockType;

    private ETypeTransaction transactionType;

    private String transactionDate;

    private double transactionValue;

    private int userId;

    public UserStock() {
    }

    public UserStock(int id, int quantity, String name, String code, ETypeStock stockType, ETypeTransaction transactionType, String transactionDate, double transactionValue, int userId) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.code = code;
        this.stockType = stockType;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionValue = transactionValue;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ETypeStock getStockType() {
        return stockType;
    }

    public void setStockType(ETypeStock stockType) {
        this.stockType = stockType;
    }

    public ETypeTransaction getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ETypeTransaction transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(double transactionValue) {
        this.transactionValue = transactionValue;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
