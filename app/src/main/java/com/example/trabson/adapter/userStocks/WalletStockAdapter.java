package com.example.trabson.adapter.userStocks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.model.dto.StockWalletRecycleViewDTO;

import java.util.List;

public class WalletStockAdapter extends RecyclerView.Adapter<WalletStockHolder> {

    private List<StockWalletRecycleViewDTO> stockList;

    private ActivityResultLauncher<Intent> result;

    public WalletStockAdapter(List<StockWalletRecycleViewDTO> stockList, ActivityResultLauncher<Intent> result) {
        this.stockList = stockList;
        this.result = result;
    }

    @NonNull
    @Override
    public WalletStockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_stock_line, parent, false);
        return new WalletStockHolder(view, result);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletStockHolder holder, int position) {
        holder.fill(stockList.get(position));
    }

    @Override
    public int getItemCount() {
        return stockList != null ?  stockList.size() : 0;
    }

    public void updateData(List<StockWalletRecycleViewDTO> newStocks) {
        if (stockList != null) {
            this.stockList.clear();
            this.stockList.addAll(newStocks);
            notifyDataSetChanged();
        }
    }
}
