package com.example.trabson.adapter.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.model.Article;
import com.squareup.picasso.Picasso;

public class NewsHolder extends RecyclerView.ViewHolder {

    private TextView titleNews, descriptionNews, fontNews, dateNews;

    private ImageView imageNews;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);

        bind();
    }

    public void fill(Article article) {
        if(!article.getTitle().equals("[Removed]")) {
            titleNews.setText(article.getTitle());
            descriptionNews.setText(article.getDescription());
            fontNews.setText(article.getUrl());
            dateNews.setText(article.getPublishedAt());
            Picasso.get()
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.logo_login)
                    .error(R.drawable.side_nav_bar)
                    .into(imageNews);
        }
    }

    public void bind() {
        titleNews = itemView.findViewById(R.id.tvTitleNews);
        descriptionNews = itemView.findViewById(R.id.tvDescriptionNews);
        fontNews = itemView.findViewById(R.id.tvFontNews);
        dateNews = itemView.findViewById(R.id.tvDateNews);
        imageNews = itemView.findViewById(R.id.ivImageNews);
    }
}
