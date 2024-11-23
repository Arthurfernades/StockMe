package com.example.trabson.model.dto;

import com.example.trabson.model.Enum.EGender;

import java.util.Date;

public class UserDTO {

    private String name, email, password;

    private Date birthDate;

    private EGender gender;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String password, Date birthDate, EGender gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
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
