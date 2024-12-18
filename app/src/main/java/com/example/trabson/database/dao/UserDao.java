package com.example.trabson.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabson.model.Enum.EGender;
import com.example.trabson.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserDao extends GenericsDao<User, Integer> {

    public UserDao(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(User obj) {
        try {
            Open();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            ContentValues cv = new ContentValues();
            cv.put("name", obj.getName());
            cv.put("email", obj.getEmail());
            cv.put("password", obj.getPassword());
            cv.put("birthDate", sdf.format(obj.getBirthDate()));
            cv.put("gender", obj.getGender().toString());

            con.insert("user", "id", cv);

        } finally {
            Close();
        }
    }

    @Override
    public void edit(User obj) {
        try {
            Open();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            ContentValues cv = new ContentValues();
            cv.put("name", obj.getName());
            cv.put("email", obj.getEmail());
            cv.put("password", obj.getPassword());
            cv.put("birthDate", sdf.format(obj.getBirthDate()));
            cv.put("gender", obj.getGender().toString());

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

                Date date = null;

                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    date= formato.parse(c.getString(4));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

//                user = new User(c.getInt(0), c.getString(1), c.getString(2),
//                c.getString(3), date, EGender.valueOf(c.getString(5)), 0.0, 0.0);
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

            Date date = null;

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            try {
                date= formato.parse(c.getString(4));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            c.moveToFirst();
            while(!c.isAfterLast()) {
//                User user = new User(c.getInt(0), c.getString(1), c.getString(2),
//                        c.getString(3), date, EGender.valueOf(c.getString(5)), c.getInt(6));

//                users.add(user);

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

    public User findByEmail(String email) {
        try {
            Open();

            String sql = "select * from user where email =  '" + email + "'";

            Cursor c = con.rawQuery(sql, null);

            User user = null;

            Date date = null;

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");


            if(c.moveToFirst()) {

                try {
                    date= formato.parse(c.getString(4));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//                user = new User(c.getInt(0), c.getString(1), c.getString(2),
//                        c.getString(3), date, EGender.valueOf(c.getString(5)), c.getInt(6));
            }

            return user;

        } finally {
            Close();
        }
    }

    public Boolean confirmByEmailAndPassword(String email, String password) {
        try {
            Open();

            String sql = "select * from user where email =  '" + email + "' and password = " + password;

            Cursor c = con.rawQuery(sql, null);

            return c.moveToFirst();

        } finally {
            Close();
        }
    }

    public Boolean emailExists(String email) {
        try {
            Open();

            String sql = "select * from user where email =  '" + email + "'";

            Cursor c = con.rawQuery(sql, null);

            return c.moveToFirst();

        } finally {
            Close();
        }
    }

}
