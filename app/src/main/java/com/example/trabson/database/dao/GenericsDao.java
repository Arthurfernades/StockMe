package com.example.trabson.database.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public abstract class GenericsDao<O, K> {

    protected DataBase db;

    private Context ctx;

    protected SQLiteDatabase con;

    public GenericsDao(Context ctx) {
        this.ctx = ctx;
        db = new DataBase(ctx);
    }

    public void Open() {
        con = db.getWritableDatabase();
    }

    public void Close() {
        con.close();
    }

    public abstract void insert(O obj);

    public abstract void edit(O obj);

    public abstract void delete(K key);

    public abstract O findById(K key);

    public abstract ArrayList<O> findAll();

    public abstract long count();
}
