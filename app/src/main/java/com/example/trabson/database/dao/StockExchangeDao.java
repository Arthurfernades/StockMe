package com.example.trabson.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabson.model.Enum.ETypeStock;
import com.example.trabson.model.Enum.ETypeTransaction;
import com.example.trabson.model.StockExchange;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StockExchangeDao extends GenericsDao<StockExchange, Integer>{

    public StockExchangeDao(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(StockExchange obj) {
        try{
            Open();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            ContentValues cv = new ContentValues();
            cv.put("quantity", obj.getQuantity());
            cv.put("name", obj.getName());
            cv.put("code", obj.getCode());
            cv.put("stockType", obj.getStockType().name());
            if(obj.getTransactionType() != null)
                cv.put("transactionType", obj.getTransactionType().name());
            if(obj.getTransactionDate() != null)
                cv.put("transactionDate", sdf.format(obj.getTransactionDate()));
            cv.put("transactionValue", obj.getTransactionValue());
            if(obj.getIdWallet() != null)
                cv.put("idWallet", obj.getIdWallet());

            con.insert("stock_exchange", "id", cv);

        } finally {
            Close();
        }
    }

    @Override
    public void edit(StockExchange obj) {
        try{
            Open();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            ContentValues cv = new ContentValues();
            cv.put("quantity", obj.getQuantity());
            cv.put("name", obj.getName());
            cv.put("code", obj.getCode());
            cv.put("stockType", obj.getStockType().name());
            if(obj.getTransactionType() != null)
                cv.put("transactionType", obj.getTransactionType().name());
            if(obj.getTransactionDate() != null)
                cv.put("transactionDate", sdf.format(obj.getTransactionDate()));
            cv.put("transactionValue", obj.getTransactionValue());
            if(obj.getIdWallet() != null)
                cv.put("idWallet", obj.getIdWallet());

            con.update("stock_exchange", cv, "id", null);

        } finally {
            Close();
        }
    }

    @Override
    public void delete(Integer key) {
        try{
            Open();

            con.delete("stock_exchange", "id = " + key, null);

        } finally {
            Close();
        }
    }

    @Override
    public StockExchange findById(Integer key) {
        try{
            Open();

            String sql = "select * from stock_exchange where id = " + key;

            Cursor c = con.rawQuery(sql, null);

            StockExchange stockExchange = null;

            if(c.moveToFirst()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

                stockExchange = new StockExchange(c.getInt(0), c.getInt(1), c.getString(2),
                        c.getString(3), ETypeStock.valueOf(c.getString(4)), ETypeTransaction.valueOf(c.getString(5)),
                        Date.valueOf(c.getString(6)), c.getDouble(7), c.getInt(8));
            }

        return stockExchange;

        } finally {
            Close();
        }
    }

    @Override
    public ArrayList<StockExchange> findAll() {
        try{
            Open();

            String sql = "select * from stock_exchange";

            Cursor c = con.rawQuery(sql, null);

            ArrayList<StockExchange> stocksExchange = new ArrayList<>();

            c.moveToFirst();
            if(c.isAfterLast()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

                StockExchange stockExchange = new StockExchange(c.getInt(0), c.getInt(1), c.getString(2),
                        c.getString(3), ETypeStock.valueOf(c.getString(4)), ETypeTransaction.valueOf(c.getString(5)),
                        Date.valueOf(c.getString(6)), c.getDouble(7), c.getInt(8));

                stocksExchange.add(stockExchange);

                c.moveToFirst();
            }

            return stocksExchange;

        } finally {
            Close();
        }
    }

    @Override
    public long count() {
        try{
            Open();

            String sql = "select count(*) from stock_exchange";
            Cursor c = con.rawQuery(sql ,null);

            c.moveToFirst();

            return c.getLong(0);

        } finally {
            Close();
        }
    }

    public ArrayList<StockExchange> findAllStocksByWalletId(int walletId) {
        try {
            Open();

            String sql = "select * from stock_exchange where idWallet = " + walletId;

            Cursor c = con.rawQuery(sql, null);

            ArrayList<StockExchange> stocksExchange = new ArrayList<>();

            c.moveToFirst();
            if(c.isAfterLast()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

                StockExchange stockExchange = new StockExchange(c.getInt(0), c.getInt(1), c.getString(2),
                        c.getString(3), ETypeStock.valueOf(c.getString(4)), ETypeTransaction.valueOf(c.getString(5)),
                        Date.valueOf(c.getString(6)), c.getDouble(7), c.getInt(8));

                stocksExchange.add(stockExchange);

                c.moveToFirst();
            }

            return stocksExchange;

        } finally {
            Close();;
        }
    }
}
