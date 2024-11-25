package com.example.trabson.service.user;

import com.example.trabson.model.User;
import com.example.trabson.model.dto.ResponseDTO;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.model.dto.LoginDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserService {

    @POST("user")
    Call<User> saveUser(@Body UserDTO userDTO);

    @GET("user")
    public Call<List<LoginDTO>> getAllUsers();

    @GET("user/{id}")
    Call<User>  getUser(@Path("id") int userId);

    @PUT("/user/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @DELETE("user/{id}")
    Call<String> deleteUser(@Path("id") int userId);

    @GET("/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);


    @POST("auth/login")
    Call<ResponseDTO> login(@Body LoginDTO LoginDTO);

}
