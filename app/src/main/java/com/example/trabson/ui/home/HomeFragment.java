package com.example.trabson.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.trabson.R;
import com.example.trabson.adapter.news.NewsAdapter;
import com.example.trabson.model.Article;
import com.example.trabson.service.news.NewsServiceImp;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    private NewsAdapter newsAdapter;

    private NewsServiceImp newsService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNews();
            }
        });

        recyclerView = view.findViewById(R.id.rvListNews);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newsService = new NewsServiceImp();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        fetchNews();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void fetchNews() {

        newsService.getAllNews(new NewsServiceImp.NewsCallback() {
            @Override
            public void onSuccess(List<Article> articles) {
                newsAdapter = new NewsAdapter(articles);
                recyclerView.setAdapter(newsAdapter);
            }

            @Override
            public void onError(String error) {
                Log.e("NewsError", "Error fetching news: " + error);
            }
        });
    }
}