package com.example.trabson;

import com.example.trabson.helper.SvgLoader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabson.model.Enum.ETypeStock;
import com.example.trabson.model.Enum.ETypeTransaction;
import com.example.trabson.model.Result;
import com.example.trabson.model.User;
import com.example.trabson.model.UserStock;
import com.example.trabson.service.stock.StockServiceImpl;
import com.example.trabson.service.user.UserServiceImp;
import com.example.trabson.service.userStock.UserStockServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailStockActivity extends AppCompatActivity {

    private StockServiceImpl stockService;

    private UserStockServiceImpl userStockService;

    private Result result;

    private ImageView companyLogo;

    private TextView company_name, company_symbol, openingPrice,
            previousClosePrice, dailyHigh, dailyLow, daylyVolume,
            peRatio, eps, regularPrice, priceVariation;

    private Button buyStock;

    private UserServiceImp userService;

    private User currentUser;

    private String email;

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

        email = getIntent().getExtras().getString("userEmail");

        stockService = new StockServiceImpl();

        userStockService = new UserStockServiceImpl();

        userService = new UserServiceImp();

        binding();

        searchUser();

        fetchStockInfo((String) getIntent().getExtras().getSerializable("code"));
    }

    private void searchUser() {

        userService.findByEmail(email, new UserServiceImp.UserCallback() {
            @Override
            public void onSuccess(User user) {
                currentUser = user;
            }

            @Override
            public void onError(String error) {
                Log.e("API USER", "Erro ao buscar o E-mail do usuário!");
            }
        });
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

        buyStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser.getBudget() > result.getRegularMarketPrice()) {

                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                    String formattedDate = outputFormat.format(new Date());

                    UserStock userStock = new UserStock(0, 1, result.getLongName(),
                            result.getSymbol(), ETypeStock.STOCK, ETypeTransaction.BUY, formattedDate,
                            result.getRegularMarketPrice(), currentUser.getId());

                    userStockService.createUserStock(userStock, new UserStockServiceImpl.UserStockServiceCallback() {
                        @Override
                        public void onSuccess(UserStock result) {
                            Toast.makeText(getApplicationContext(), "Compra realizada com sucesso!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getApplicationContext(), "Erro na compra!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Saldo insuficiente para compra", Toast.LENGTH_LONG).show();
                }


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