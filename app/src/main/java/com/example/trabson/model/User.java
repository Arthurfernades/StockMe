package com.example.trabson.model;

import com.example.trabson.model.Enum.EGender;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    private int id;

    @SerializedName("wallet")
    private Integer idWallet;

    private String name, email, password;

    private Date birthDate;

    private EGender gender;

    public User() { }

    public User(int id, String name, String email, String password, Date birthDate, EGender gender, Integer idWallet) {
        this.id = id;
        this.idWallet = idWallet;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdWallet() {
        return idWallet;
    }

    public void setIdWallet(Integer idWallet) {
        this.idWallet = idWallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }
}
