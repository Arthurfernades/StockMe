package com.example.trabson.service.userStock;

import android.util.Log;

import com.example.trabson.config.RetrofitConfig;
import com.example.trabson.model.UserStock;
import com.example.trabson.service.user.UserServiceImp;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStockServiceImpl {

    private IUserStockService userStockService;

    public UserStockServiceImpl() {
        userStockService = new RetrofitConfig().getUserStockRetrofit().create(IUserStockService.class);
    }

    public void createUserStock(UserStock userStock, final UserStockServiceCallback callback) {
        Call<UserStock> call = userStockService.createUserStock(userStock);

        call.enqueue(new Callback<UserStock>() {
            @Override
            public void onResponse(Call<UserStock> call, Response<UserStock> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("API Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<UserStock> call, Throwable throwable) {
                Log.e("API", "Failure: " + throwable.getMessage());
                callback.onError("Erro ao tentar se conectar a API");
            }
        });
    }

    public interface UserStockServiceCallback {
        void onSuccess(UserStock result);
        void onError(String error);
    }

}
