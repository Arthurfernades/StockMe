package com.example.trabson.adapter.stocks;

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
import com.example.trabson.helper.SvgLoader;
import com.example.trabson.model.Stock;

public class StockHolder extends RecyclerView.ViewHolder {

    private TextView stockCode, nameNSector, stockClose;

    private ImageView stockSvg;

    private Context ctx;

    private Stock stock;

    private ActivityResultLauncher<Intent> result;

    private String userEmail;

    public StockHolder(@NonNull View itemView, ActivityResultLauncher<Intent> result, String userEmail) {
        super(itemView);
        this.ctx = itemView.getContext();
        this.result = result;
        this.userEmail = userEmail;
        bind();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(view.getContext(), DetailStockActivity.class);
                itn.putExtra("code", stock.getStock());
                itn.putExtra("userEmail", userEmail);
                result.launch(itn);
            }
        });
    }

    public void fill(Stock stock) {
        this.stock = stock;
        stockCode.setText(stock.getStock());
        nameNSector.setText(stock.getName() + " - " + stock.getSector());
        stockClose.setText("R$" + stock.getClose());
        SvgLoader svgLoader = new SvgLoader();
        svgLoader.loadSvg(ctx, stock.getLogo(), stockSvg);
    }

    public void bind() {
        stockCode = itemView.findViewById(R.id.tvStockCode);
        nameNSector = itemView.findViewById(R.id.tvNameNSector);
        stockClose = itemView.findViewById(R.id.tvStockClose);
        stockSvg = itemView.findViewById(R.id.ivStockSvg);
    }
}
