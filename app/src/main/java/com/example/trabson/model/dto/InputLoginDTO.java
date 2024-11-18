package com.example.trabson.model.dto;

public class InputLoginDTO {

    private String email, password;

    public InputLoginDTO() {
    }

    public InputLoginDTO(String email, String password) {
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
}
