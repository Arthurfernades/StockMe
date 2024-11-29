package com.example.trabson.model.dto;

public class UserStockDTO {

    private int id, quantity;

    private String name, code, stockType, transactionType, lastTransactionDate;

    private double amountValue;

    private int userId;

    public UserStockDTO() {
    }

    public UserStockDTO(int id, int quantity, String name, String code, String stockType, String transactionType, String lastTransactionDate, double amountValue, int userId) {
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

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setTLastTransactionDate(String lastTransactionDate) {
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
