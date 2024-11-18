package com.example.trabson.model.dto;

public class UserDTO {

    private String access_token, refresh_token, email, password;

    public UserDTO() {
    }

    public UserDTO(String access_token, String refresh_token, String email, String password) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.email = email;
        this.password = password;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
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
