package com.example.trabson.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.trabson.R;
import com.example.trabson.adapter.news.NewsAdapter;
import com.example.trabson.model.Article;
import com.example.trabson.service.news.NewsServiceImp;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsServiceImp newsService;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rvListNews);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);
        newsAdapter = new NewsAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(newsAdapter);

        newsService = new NewsServiceImp();

        swipeRefreshLayout.setOnRefreshListener(() -> fetchNews());

        fetchNews();

        return view;
    }

    private void fetchNews() {
        swipeRefreshLayout.setRefreshing(true);

        newsService.getAllNews(new NewsServiceImp.NewsCallback() {
            @Override
            public void onSuccess(List<Article> articles) {
                newsAdapter.updateData(articles);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String error) {
                Log.e("NewsError", "Error fetching news: " + error);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
