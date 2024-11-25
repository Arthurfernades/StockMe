package com.example.trabson.service.news;

import com.example.trabson.model.User;
import com.example.trabson.model.dto.LoginDTO;
import com.example.trabson.model.dto.NewsDTO;
import com.example.trabson.model.dto.ResponseDTO;
import com.example.trabson.model.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface INewsService {

    @GET("v2/everything?q=business&language=pt&apiKey=387cfa7c9bac4e09bc55903a276569a3")
    Call<NewsDTO> getAllNews();

}
