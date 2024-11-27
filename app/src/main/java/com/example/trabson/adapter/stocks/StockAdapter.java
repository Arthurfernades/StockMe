package com.example.trabson.adapter.stocks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.model.Article;
import com.example.trabson.model.Stock;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockHolder> {

    private List<Stock> stockList;

    private ActivityResultLauncher<Intent> result;

    private String userEmail;

    public StockAdapter(List<Stock> stockList, ActivityResultLauncher<Intent> result, String userEmail) {
        this.stockList = stockList;
        this.result = result;
        this.userEmail = userEmail;
    }

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_line, parent, false);
        return new StockHolder(view, result, userEmail);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        holder.fill(stockList.get(position));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public void updateData(List<Stock> newStocks) {
        this.stockList.clear();
        this.stockList.addAll(newStocks);
        notifyDataSetChanged();
    }
}
