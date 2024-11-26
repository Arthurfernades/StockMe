package com.example.trabson.model.dto;

import com.example.trabson.model.Index;
import com.example.trabson.model.Stock;

import java.util.List;

public class StockResponseDTO {

    private List<Index> indexes;

    private List<Stock> stocks;

    private List<String> availableSectors;

    private List<String> availableStockTypes;

    public StockResponseDTO() {
    }

    public StockResponseDTO(List<Stock> stocks, List<Index> indexes, List<String> availableSectors, List<String> availableStockTypes) {
        this.stocks = stocks;
        this.indexes = indexes;
        this.availableSectors = availableSectors;
        this.availableStockTypes = availableStockTypes;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<String> getAvailableSectors() {
        return availableSectors;
    }

    public void setAvailableSectors(List<String> availableSectors) {
        this.availableSectors = availableSectors;
    }

    public List<String> getAvailableStockTypes() {
        return availableStockTypes;
    }

    public void setAvailableStockTypes(List<String> availableStockTypes) {
        this.availableStockTypes = availableStockTypes;
    }
}
