package com.example.trabson.service.userStock;

import com.example.trabson.model.UserStock;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserStockService {

    @POST("stocks")
    Call<UserStock> createUserStock(@Body UserStock userStock);

    @GET("stocks/{userId}")
    Call<UserStock> getByUserId(@Path("userId") int userId);

    @PUT("stocks/{id}")
    Call<UserStock> updateStock(@Path("id") int id);

}
