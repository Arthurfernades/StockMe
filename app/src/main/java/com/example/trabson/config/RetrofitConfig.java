package com.example.trabson.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private Retrofit stockRetrofit, newsRetrofit, userRetrofit, walletRetrofit, userStockRetrofit;

    public RetrofitConfig() {

        userRetrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsRetrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stockRetrofit = new Retrofit.Builder()
                .baseUrl("https://brapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userStockRetrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getStockRetrofit() {
        return stockRetrofit;
    }

    public Retrofit getNewsRetrofit() {
        return newsRetrofit;
    }

    public Retrofit getUserRetrofit() {
        return userRetrofit;
    }

    public Retrofit getUserStockRetrofit() {
        return userStockRetrofit;
    }
}
