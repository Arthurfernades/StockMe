package com.example.trabson.adapter.stocks;

import static com.example.trabson.helper.SvgLoader.loadSvg;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.model.Stock;

public class StockHolder extends RecyclerView.ViewHolder {

    private TextView stockCode, nameNSector, stockClose;

    private ImageView stockSvg;

    private Context ctx;

    public StockHolder(@NonNull View itemView, Context ctx) {
        super(itemView);
        this.ctx = ctx;
        bind();
    }

    public void fill(Stock stock) {

        stockCode.setText(stock.getStock());
        nameNSector.setText(stock.getName() + " - " + stock.getSector());
        stockClose.setText("R$" + stock.getClose());
        loadSvg(ctx, stock.getLogo(), stockSvg);
    }

    public void bind() {
        stockCode = itemView.findViewById(R.id.tvStockCode);
        nameNSector = itemView.findViewById(R.id.tvNameNSector);
        stockClose = itemView.findViewById(R.id.tvStockClose);
        stockSvg = itemView.findViewById(R.id.ivStockSvg);
    }
}
