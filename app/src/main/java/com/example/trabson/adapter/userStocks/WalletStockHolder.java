package com.example.trabson.adapter.userStocks;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.DetailStockActivity;
import com.example.trabson.R;
import com.example.trabson.model.dto.StockWalletRecycleViewDTO;

public class WalletStockHolder extends RecyclerView.ViewHolder {

    private TextView stockCode, userStockProfit, currentPrice;

    private ImageView walletStockSvg;

    private Context ctx;

    private StockWalletRecycleViewDTO stock;

    private ActivityResultLauncher<Intent> result;

    public WalletStockHolder(@NonNull View itemView, ActivityResultLauncher<Intent> result) {
        super(itemView);
        this.ctx = itemView.getContext();
        this.result = result;
        bind();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(view.getContext(), DetailStockActivity.class);
                itn.putExtra("code", stock.getCode());
                result.launch(itn);
            }
        });
    }

    public void fill(StockWalletRecycleViewDTO stock) {
        this.stock = stock;
        stockCode.setText(stock.getCode());

        /*double stockProfit = stock.getAmuntPayedPrice() - (stock.getAmuntCurrentPrice() * stock.getQuantity());

        if(stockProfit < 0) {
            userStockProfit.setText("-R$" + stockProfit);
            userStockProfit.setTextColor(itemView.getResources().getColor(R.color.red));
        } else {
            userStockProfit.setText("+R$" + stockProfit);
            userStockProfit.setTextColor(itemView.getResources().getColor(R.color.green));
        }*/

        /*currentPrice.setText("R$" + stock.getAmuntCurrentPrice());
        SvgLoader svgLoader = new SvgLoader();
        svgLoader.loadSvg(ctx, stock.getSvgUrl(), walletStockSvg);*/
    }

    public void bind() {
        stockCode = itemView.findViewById(R.id.tvWalletStockCode);
    }
}
