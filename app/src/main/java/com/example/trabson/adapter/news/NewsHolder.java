package com.example.trabson.adapter.news;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.model.Article;
import com.squareup.picasso.Picasso;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class NewsHolder extends RecyclerView.ViewHolder {

    private TextView titleNews, descriptionNews, fontNews, dateNews;

    private ImageView imageNews;

    private Context ctx;

    public NewsHolder(@NonNull View itemView, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        bind();
    }

    public void fill(Article article) {
            titleNews.setText(article.getTitle());
            descriptionNews.setText(article.getDescription());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            ZonedDateTime parsedDate = ZonedDateTime.parse(article.getPublishedAt(), inputFormatter);
            String formattedDate = parsedDate.format(outputFormatter);
            dateNews.setText(formattedDate);
        } else {
            dateNews.setText(article.getPublishedAt());
        }

            Picasso.get()
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.loading_icon)
                    .error(R.drawable.erro_icon)
                    .into(imageNews);

        imageNews.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            startActivity(ctx, browserIntent, null);
        });
    }

    public void bind() {
        titleNews = itemView.findViewById(R.id.tvTitleNews);
        descriptionNews = itemView.findViewById(R.id.tvDescriptionNews);
        dateNews = itemView.findViewById(R.id.tvDateNews);
        imageNews = itemView.findViewById(R.id.ivImageNews);
    }
}
