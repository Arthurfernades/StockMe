package com.example.trabson.model.dto;

import com.example.trabson.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockResponseDTO {

    private List<Stock> stockList;

    public StockResponseDTO() {
    }

    public StockResponseDTO(List<Stock> stockList) {
        this.stockList = new ArrayList<>();
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
