package com.example.stockapi.service;

import com.example.stockapi.model.Stock;
import com.example.stockapi.model.User;
import com.example.stockapi.model.repository.StockRepository;
import com.example.stockapi.model.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Stock> getStocksByWalletId(Long userId) {
        List<Stock> stocks = stockRepository.findByUserId(userId);
        if(stocks != null && !stocks.isEmpty()) {
            return stocks;
        } else {
            return stocks != null ? stocks : Collections.emptyList();
        }
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with id " + id));
    }

    public Stock getStockByCode(String code, long userId) {
        List<Stock> stockList = stockRepository.findByUserId(userId);
        for (Stock stock : stockList) {
            if(stock.getCode().equals(code)) {
                return stock;
            }
        }
        return new Stock();
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stockDetails) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with id " + id));
        stock.setQuantity(stockDetails.getQuantity());
        stock.setName(stockDetails.getName());
        stock.setCode(stockDetails.getCode());
        stock.setStockType(stockDetails.getStockType());
        stock.setTransactionType(stockDetails.getTransactionType());
        stock.setLastTransactionDate(stockDetails.getLastTransactionDate());
        stock.setAmountValue(stockDetails.getAmountValue());
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with id " + id));
        stockRepository.delete(stock);
    }
}

