package com.example.trabson.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabson.model.User;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserDao extends GenericsDao<User, Integer> {

    public UserDao(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(User obj) {
        try {
            Open();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            ContentValues cv = new ContentValues();
            cv.put("name", obj.getName());
            cv.put("email", obj.getEmail());
            cv.put("password", obj.getPassword());
            cv.put("birthDate", sdf.format(obj.getBirthDate()));
            cv.put("idWallet", obj.getIdWallet());

            con.insert("user", "id", cv);

        } finally {
            Close();
        }
    }

    @Override
    public void edit(User obj) {
        try {
            Open();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            ContentValues cv = new ContentValues();
            cv.put("name", obj.getName());
            cv.put("email", obj.getEmail());
            cv.put("password", obj.getPassword());
            cv.put("birthDate", sdf.format(obj.getBirthDate()));

            con.update("user", cv, "id = " + obj.getId(), null);

        } finally {
            Close();
        }
    }

    @Override
    public void delete(Integer key) {
        try {
            Open();

            con.delete("user", "id = " + key, null);

        } finally {
            Close();
        }
    }

    @Override
    public User findById(Integer key) {
        try {
            Open();

            String sql = "select * from user where id = " + key;

            Cursor c = con.rawQuery(sql, null);

            User user = null;

            if(c.moveToFirst()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

                user = new User(c.getInt(0), c.getString(1), c.getString(2),
                c.getString(3), Date.valueOf(c.getString(4)), c.getInt(5));
            }

            return user;

        } finally {
            Close();
        }

    }

    @Override
    public ArrayList<User> findAll() {
        try {
            Open();

            String sql = "select * from user";

            Cursor c = con.rawQuery(sql, null);

            ArrayList<User> users = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            c.moveToFirst();
            while(!c.isAfterLast()) {
                User user = new User(c.getInt(0), c.getString(1), c.getString(2),
                        c.getString(3), Date.valueOf(c.getString(4)), c.getInt(5));

                users.add(user);

                c.moveToNext();
            }

            return users;

        } finally {
            Close();
        }
    }

    @Override
    public long count() {
        try {
            Open();

            String sql = "select count(*) from user";
            Cursor c = con.rawQuery(sql ,null);

            c.moveToFirst();

            return c.getLong(0);

        } finally {
            Close();
        }
    }

    public Boolean confirmByEmailAndPassword(String email, String password) {
        try {
            Open();

            String sql = "select * from user where email = " + email + " and password = " + password;

            Cursor c = con.rawQuery(sql, null);

            return c.moveToFirst();

        } finally {
            Close();
        }

    }

}
