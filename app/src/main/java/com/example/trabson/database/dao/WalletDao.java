package com.example.trabson.database.dao;

import android.content.Context;

import com.example.trabson.model.Wallet;

import java.util.ArrayList;

public class WalletDao extends GenericsDao<Wallet, Integer>{

    public WalletDao(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Wallet obj) {

    }

    @Override
    public void edit(Wallet obj) {

    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public Wallet findById(Integer key) {
        return null;
    }

    @Override
    public ArrayList<Wallet> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
