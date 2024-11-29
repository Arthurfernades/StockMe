package com.example.trabson.service.userStock;

import com.example.trabson.model.UserStock;
import com.example.trabson.model.dto.UserStockDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserStockService {

    @POST("stocks")
    Call<UserStock> createUserStock(@Body UserStock userStock);

    @GET("stocks/{id}")
    Call<UserStock> getUserStockById(@Path("id") int id);

    @PUT("stocks/{id}")
    Call<UserStock> updateUserStock(@Path("id") int id, @Body UserStockDTO userStock);

    @GET("stocks/user/{userId}")
    Call<List<UserStockDTO>> getByUserId(@Path("userId") int userId);

    @GET("stocks/{userId}/{code}")
    Call<UserStock> getUserStockByCode(@Path("userId") int userId,
                                       @Path("code") String code);

}
