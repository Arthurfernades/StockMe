package com.example.trabson.ui.stock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.trabson.R;
import com.example.trabson.adapter.stocks.StockAdapter;
import com.example.trabson.model.Stock;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        stockAdapter = new StockAdapter(new ArrayList<>(), viewStockInfo);
        recyclerView.setAdapter(stockAdapter);

        stockService = new StockServiceImpl();

        swipeRefreshLayout.setOnRefreshListener(() -> fetchStockList());

        fetchStockList();

        return view;
    }

    ActivityResultLauncher<Intent> viewStockInfo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {

                }
            }
    );

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