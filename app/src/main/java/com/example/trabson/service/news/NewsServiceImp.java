package com.example.trabson.service.news;

import android.util.Log;

import com.example.trabson.config.RetrofitConfig;
import com.example.trabson.model.Article;
import com.example.trabson.model.dto.NewsDTO;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsServiceImp {

    private INewsService newsService;

    private List<Article> newsList;


    public NewsServiceImp() {
        newsService = new RetrofitConfig().getNewsRetrofit().create(INewsService.class);
    }

    public void getAllNews(final NewsCallback callback) {

        Call<NewsDTO> call = newsService.getAllNews();

        call.enqueue(new Callback<NewsDTO>() {
            @Override
            public void onResponse(Call<NewsDTO> call, Response<NewsDTO> response) {

                Log.d("TAG",response.code()+"");

                NewsDTO resource = response.body();

                if (response.isSuccessful() && response.body() != null) {
                    List<Article> newsList = resource.getArticles();

                    List<Article> filteredNewsList = newsList.stream()
                            .filter(article -> !"[Removed]".equals(article.getTitle()) &&
                                    !"[Removed]".equals(article.getDescription()) &&
                                    !"[Removed]".equals(article.getSource().getName()))
                            .collect(Collectors.toList());

                    callback.onSuccess(filteredNewsList);
                } else {
                    callback.onError("Falha na resposta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NewsDTO> call, Throwable throwable) {
                callback.onError(throwable.getMessage());
            }
        });
    }

    public interface NewsCallback {
        void onSuccess(List<Article> articles);
        void onError(String error);
    }

}
