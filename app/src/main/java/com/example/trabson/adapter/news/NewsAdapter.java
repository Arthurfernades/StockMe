package com.example.trabson.adapter.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.model.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<Article> articles;

    private Context ctx;

    public NewsAdapter(List<Article> articles, Context ctx) {
        this.articles = articles;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_line, parent, false);
        return new NewsHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.fill(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateData(List<Article> newArticles) {
        this.articles.clear();
        this.articles.addAll(newArticles);
        notifyDataSetChanged();
    }
}
