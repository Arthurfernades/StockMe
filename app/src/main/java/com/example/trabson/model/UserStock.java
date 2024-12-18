package com.example.trabson.model;

import com.example.trabson.model.Enum.ETypeStock;
import com.example.trabson.model.Enum.ETypeTransaction;

public class UserStock {

    private int id, quantity;

    private String name, code;

    private ETypeStock stockType;

    private ETypeTransaction transactionType;

    private String lastTransactionDate;

    private double amountValue;

    private int userId;

    public UserStock() {
    }

    public UserStock(int id, int quantity, String name, String code, ETypeStock stockType, ETypeTransaction transactionType, String lastTransactionDate, double amountValue, int userId) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.code = code;
        this.stockType = stockType;
        this.transactionType = transactionType;
        this.lastTransactionDate = lastTransactionDate;
        this.amountValue = amountValue;
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

    public String getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(String lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public double getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(double amountValue) {
        this.amountValue = amountValue;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
