package com.example.trabson.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabson.model.Wallet;

import java.util.ArrayList;

public class WalletDao extends GenericsDao<Wallet, Integer>{

    public WalletDao(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Wallet obj) {
        try {
            Open();

            ContentValues cv = new ContentValues();
            cv.put("budget", obj.getBudget());
            cv.put("profitability", obj.getProfitability());

            con.insert("wallet", "id", cv);

        } finally {
            Close();
        }
    }

    @Override
    public void edit(Wallet obj) {
        try {
            Open();

            ContentValues cv = new ContentValues();
            cv.put("budget", obj.getBudget());
            cv.put("profitability", obj.getProfitability());

            con.update("wallet", cv, "id = " + obj.getId(), null);

        } finally {
            Close();
        }
    }

    @Override
    public void delete(Integer key) {
        try {
            Open();

            con.delete("wallet", "id = " + key, null);

        } finally {
            Close();
        }
    }

    @Override
    public Wallet findById(Integer key) {
        try {
            Open();

            String sql = "select * from wallet where id = " + key;

            Cursor c = con.rawQuery(sql, null);

            Wallet wallet = null;

            if(c.moveToFirst()) {

                wallet = new Wallet(c.getInt(0), c.getDouble(1), c.getDouble(2), null);
            }

            return wallet;

        } finally {
            Close();
        }
    }

    @Override
    public ArrayList<Wallet> findAll() {
        try {
            Open();

            String sql = "select * from wallet";

            Cursor c = con.rawQuery(sql, null);

            ArrayList<Wallet> wallets = null;

            c.moveToFirst();
            while(!c.isAfterLast()) {

                Wallet wallet = new Wallet(c.getInt(0), c.getDouble(1), c.getDouble(2), null);

                wallets.add(wallet);

                c.moveToNext();
            }

            return wallets;

        } finally {
            Close();
        }
    }

    @Override
    public long count() {
        try {
            Open();

            String sql = "select count(*) from wallet";
            Cursor c = con.rawQuery(sql ,null);

            c.moveToFirst();

            return c.getLong(0);

        } finally {
            Close();
        }
    }
}