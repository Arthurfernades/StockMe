package com.example.trabson.database.dao;

import android.content.Context;

import com.example.trabson.model.StockExchange;

import java.util.ArrayList;

public class StockExchangeDao extends GenericsDao<StockExchange, Integer>{

    public StockExchangeDao(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(StockExchange obj) {

    }

    @Override
    public void edit(StockExchange obj) {

    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public StockExchange findById(Integer key) {
        return null;
    }

    @Override
    public ArrayList<StockExchange> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
