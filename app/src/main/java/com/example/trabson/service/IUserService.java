package com.example.trabson.service;

import com.example.trabson.model.User;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.model.dto.UserLoginDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserService {

    @GET("users")
    public Call<UserLoginDTO> getAllUsers();

    @GET("users/{id}")
    public Call<User>  getUser(@Path("id") int userId);

    @POST("users")
    public Call<User> saveUser(@Body UserDTO userDTO);

    @DELETE("users/{id}")
    public Call<String> deleteUser(@Path("id") int userId);

    @POST("users/login")
    public Call<String> login(@Body UserLoginDTO userLoginDTO);

}
