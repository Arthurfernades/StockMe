package com.example.trabson;

import com.example.trabson.helper.SvgLoader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabson.model.Result;
import com.example.trabson.service.stock.StockServiceImpl;

import java.util.List;

public class DetailStockActivity extends AppCompatActivity {

    private StockServiceImpl stockService;

    private Result result;

    private ImageView companyLogo;

    private TextView company_name, company_symbol, openingPrice,
            previousClosePrice, dailyHigh, dailyLow, daylyVolume,
            peRatio, eps, regularPrice, priceVariation;

    private Button buyStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_stock);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stockService = new StockServiceImpl();

        binding();

        fetchStockInfo((String) getIntent().getExtras().getSerializable("code"));
    }

    private void fetchStockInfo(String code) {

        stockService.getStockInfo(code, new StockServiceImpl.StockInfoCallback() {
            @Override
            public void onSuccess(List<Result> results) {
                result = results.get(0);

                company_name.setText(result.getLongName());
                company_symbol.setText(result.getSymbol());
                openingPrice.setText("R$" + result.getRegularMarketOpen());
                previousClosePrice.setText("R$" + result.getRegularMarketPreviousClose());
                dailyHigh.setText("R$" + result.getRegularMarketDayHigh());
                dailyLow.setText("R$" + result.getRegularMarketDayLow());
                daylyVolume.setText(String.valueOf(result.getRegularMarketVolume()));
                peRatio.setText(String.valueOf(result.getPriceEarnings()));
                eps.setText("R$" + result.getEarningsPerShare());
                regularPrice.setText("R$" + result.getRegularMarketPrice());
                priceVariation.setText("Variação: " + result.getRegularMarketChange() + result.getRegularMarketChangePercent());
                SvgLoader svgLoader = new SvgLoader();
                svgLoader.loadSvg(getApplicationContext(), result.getLogoUrl(), companyLogo);
            }

            @Override
            public void onError(String error) {
                Log.e("BRAPI", "Error fetching Stock  Info: " + error);
            }
        });
    }

    private void binding() {
        company_name = findViewById(R.id.tvCompanyName);
        company_symbol = findViewById(R.id.tvCompanySymbol);
        openingPrice = findViewById(R.id.tvOpeningPrice);
        previousClosePrice = findViewById(R.id.tvPreviousClosePrice);
        dailyHigh = findViewById(R.id.tvDailyHigh);
        dailyLow = findViewById(R.id.tvDailyLow);
        daylyVolume = findViewById(R.id.tvDaylyVolume);
        peRatio = findViewById(R.id.tvPeRatio);
        eps = findViewById(R.id.tvEps);
        regularPrice = findViewById(R.id.tvRegularPrice);
        priceVariation = findViewById(R.id.tvPriceVariation);
        companyLogo = findViewById(R.id.ivCompanyLogo);
        buyStock = findViewById(R.id.btnBuyStock);
    }
}