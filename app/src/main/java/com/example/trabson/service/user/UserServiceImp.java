package com.example.trabson.service.user;

import android.util.Log;

import com.example.trabson.config.RetrofitConfig;
import com.example.trabson.model.User;
import com.example.trabson.model.dto.ResponseDTO;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.model.dto.LoginDTO;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceImp {

    private final IUserService userService;

    public UserServiceImp() {
        userService = new RetrofitConfig().getUserRetrofit().create(IUserService.class);
    }

    public void createUser(UserDTO userDTO, final AuthServiceCallback callback) {
        Call<User> callCreateUser = userService.saveUser(userDTO);

        callCreateUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Successful");
                } else {
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e("API", "Error in response: " + errorMessage);

                        if (errorMessage.contains("Email already exists")) {
                            callback.onError("Email already exists");
                        } else {
                            callback.onError("API Error: " + errorMessage);
                        }
                    } catch (IOException e) {
                        Log.e("API", "Error processing response body: " + e.getMessage());
                        callback.onError("API Error" + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.e("API", "Failure: " + throwable.getMessage());
                callback.onError("Erro ao tentar se conectar a API");
            }
        });
    }

    public void authorizeLogin(LoginDTO loginDTO, final AuthServiceCallback callback) {
        Call<ResponseDTO> callLogin = userService.login(loginDTO);

        callLogin.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                Log.d("API Response (authorizeLogin)", "Code: " + response.code());
                Log.d("API Response (authorizeLogin)", "Body: " + response.body());
                Log.d("Raw Response (authorizeLogin)", response.raw().toString());

                if(response.code() == 200) {
                    callback.onSuccess("Authorized");
                } else if(response.code() == 401) {
                    callback.onError("Invalid credentials");
                } else if(response.code() == 404) {
                    callback.onError("Email not registered");
                } else {
                    callback.onError("Invalid");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable throwable) {
                Log.e("API", "Failure: " + throwable.getMessage());
                callback.onError("Erro ao tentar se conectar a API");
            }
        });
    }

    public void findByEmail(String email, final UserCallback callback) {
        Call<User> callUser = userService.getUserByEmail(email);

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                callback.onError("Failure: " + throwable.getMessage());
            }
        });
    }

    public interface AuthServiceCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    public interface UserCallback {
        void onSuccess(User user);
        void onError(String error);
    }
}
