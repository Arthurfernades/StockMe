package com.example.trabson.service.userStock;

import android.util.Log;

import com.example.trabson.config.RetrofitConfig;
import com.example.trabson.model.UserStock;
import com.example.trabson.model.dto.UserStockDTO;

import java.util.List;

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

    public void getUserStockById(int id, final UserStockServiceCallback callback) {
        Call<UserStock> call = userStockService.getUserStockById(id);

        call.enqueue(new Callback<UserStock>() {
            @Override
            public void onResponse(Call<UserStock> call, Response<UserStock> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("API USER STOCK", "Erro ao buscar a ação pelo Id");
                }
            }

            @Override
            public void onFailure(Call<UserStock> call, Throwable throwable) {
                Log.e("API USER STOCK", "Failure: " + throwable.getMessage());
                callback.onError("Erro ao tentar se conectar a API");
            }
        });
    }

    public void updateUserStock(int id, UserStockDTO userStock, final UserStockServiceCallback callback) {
        Call<UserStock> call = userStockService.updateUserStock(id, userStock);

        call.enqueue(new Callback<UserStock>() {
            @Override
            public void onResponse(Call<UserStock> call, Response<UserStock> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserStock> call, Throwable throwable) {
                callback.onError("Failure: " + throwable.getMessage());
            }
        });
    }

    public void getUserStockByUserId(int userId, final UserStockServiceListCallback callback) {
        Call<List<UserStockDTO>> call = userStockService.getByUserId(userId);

        call.enqueue(new Callback<List<UserStockDTO>>() {
            @Override
            public void onResponse(Call<List<UserStockDTO>> call, Response<List<UserStockDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("API Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<UserStockDTO>> call, Throwable throwable) {
                Log.e("API", "Failure: " + throwable.getMessage());
                callback.onError("Erro ao tentar se conectar a API");
            }
        });
    }

    public void getUserStockByCode(int userId, String code, final UserStockServiceCallback callback) {
        Call<UserStock> call = userStockService.getUserStockByCode(userId, code);

        call.enqueue(new Callback<UserStock>() {
            @Override
            public void onResponse(Call<UserStock> call, Response<UserStock> response) {
                if (response.isSuccessful() && response.body().getCode() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onSuccess(null);
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

    public interface UserStockServiceListCallback {
        void onSuccess(List<UserStockDTO> result);
        void onError(String error);
    }

}
