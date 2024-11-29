package com.example.stockapi.model.repository;

import com.example.stockapi.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByUserId(Long userId);
    Stock findByCode(String code);
}