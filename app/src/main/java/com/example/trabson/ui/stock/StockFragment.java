package com.example.trabson.ui.stock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.trabson.R;
import com.example.trabson.adapter.news.NewsAdapter;
import com.example.trabson.adapter.stocks.StockAdapter;
import com.example.trabson.model.Article;
import com.example.trabson.model.Stock;
import com.example.trabson.service.news.NewsServiceImp;
import com.example.trabson.service.stock.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class StockFragment extends Fragment {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private StockAdapter stockAdapter;

    private StockServiceImpl stockService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        recyclerView = view.findViewById(R.id.rvStockList);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutStockList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stockAdapter = new StockAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(stockAdapter);

        stockService = new StockServiceImpl();

        swipeRefreshLayout.setOnRefreshListener(() -> fetchStockList());

        fetchStockList();

        return view;
    }

    private void fetchStockList() {
        swipeRefreshLayout.setRefreshing(true);

        stockService.getAllStocks("stock", new StockServiceImpl.StockCallback() {
            @Override
            public void onSuccess(List<Stock> stocks) {
                stockAdapter.updateData(stocks);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}