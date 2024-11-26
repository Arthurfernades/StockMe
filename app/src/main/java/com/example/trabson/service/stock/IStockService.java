package com.example.trabson.service.stock;

import com.example.trabson.model.dto.StockResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IStockService {

    @GET("quote/list")
    Call<StockResponseDTO> getStockList(
            @Query("type") String type,
            @Query("token") String token
    );
}
