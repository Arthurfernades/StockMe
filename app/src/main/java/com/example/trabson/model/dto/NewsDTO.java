package com.example.trabson.model.dto;

import com.example.trabson.model.Article;

import java.util.List;

public class NewsDTO {

    private String status;

    private int totalResults;

    private List<Article> articles;

    public NewsDTO() {
    }

    public NewsDTO(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}

