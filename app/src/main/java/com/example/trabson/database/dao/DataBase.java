package com.example.trabson.database.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "stockMe.db";
    private static final int DB_VERSION = 1;

    public DataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_WALLET);
        sqLiteDatabase.execSQL(CREATE_TABLE_STOCK_EXCHANGE);
    }

    private static final String CREATE_TABLE_USER = "create table user (" +
            "id integer primary key autoincrement," +
            "name text not null," +
            "email text not null," +
            "password text not null," +
            "birthDate text not null," +
            "idWallet integer" +
            ")";

    private static final String CREATE_TABLE_WALLET = "create table wallet (" +
            "id integer primary key autoincrement," +
            "budget double not null," +
            "profitability double not null" +
            ")";

    private static final String CREATE_TABLE_STOCK_EXCHANGE = "create table stock_exchange (" +
            "id integer primary key autoincrement," +
            "quantity int not null," +
            "name text not null," +
            "code text not null," +
            "stockType text not null," +
            "transactionType text," +
            "transactionDate text," +
            "transactionValue double not null," +
            "idWallet integer" +
            ")";

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
