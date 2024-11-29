package com.example.trabson;

import com.example.trabson.helper.SvgLoader;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.model.dto.UserStockDTO;
import com.example.trabson.service.stock.StockServiceImpl;
import com.example.trabson.service.user.UserServiceImp;
import com.example.trabson.service.userStock.UserStockServiceImpl;
import com.google.android.material.textfield.TextInputLayout;

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
            peRatio, eps, regularPrice, priceVariation, stockInWallet;

    private Button buyStock, saleStock;

    private UserServiceImp userService;

    private User currentUser;

    private String email;

    private int quantity;

    private TextInputLayout cpTotalQuantity;

    private ImageButton ibPlus, ibMinus;

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

        SharedPreferences prefs = getSharedPreferences("StockMe", MODE_PRIVATE);

        email = prefs.getString("email", null);

        stockService = new StockServiceImpl();

        userStockService = new UserStockServiceImpl();

        userService = new UserServiceImp();

        binding();

        searchUser();

        fetchStockInfo((String) getIntent().getExtras().getSerializable("code"));

        quantity = 1;

        cpTotalQuantity.getEditText().setText(String.valueOf(quantity));

        ibPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                cpTotalQuantity.getEditText().setText(String.valueOf(quantity));
                regularPrice.setText("R$" + (result.getRegularMarketPrice() * quantity));
            }
        });

        ibMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity > 1) {
                    quantity--;
                    cpTotalQuantity.getEditText().setText(String.valueOf(quantity));
                    regularPrice.setText("R$" + (result.getRegularMarketPrice() * quantity));
                }
            }
        });

        buyStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser.getBudget() > result.getRegularMarketPrice() * quantity) {
                    registerBuy();

                } else {
                    Toast.makeText(getApplicationContext(), "Saldo insuficiente para compra", Toast.LENGTH_LONG).show();
                }
            }
        });

        saleStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cleanedValue = (stockInWallet.getText().toString()).replace("Quantidade na carteira: ", "").trim();
                if(Integer.parseInt(cpTotalQuantity.getEditText().getText().toString()) < Integer.parseInt(cleanedValue)) {
                    userStockService.getUserStockByCode(currentUser.getId(), (String) getIntent().getExtras().getSerializable("code"), new UserStockServiceImpl.UserStockServiceCallback() {
                        @Override
                        public void onSuccess(UserStock result) {
                            UserStock userStock = result;

                            userStock.setQuantity(userStock.getQuantity() - Integer.parseInt(cpTotalQuantity.getEditText().getText().toString()));

                            String cleanedValue = (regularPrice.getText().toString()).replace("R$", "").trim();

                            userStock.setAmountValue(userStock.getQuantity() * Double.parseDouble(cleanedValue));

                            cpTotalQuantity.getEditText().getText();

                            updateStock(userStock, "Venda");

                            updateBudget("venda");
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("API USER STOCK", error);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Você possui apenas " + cleanedValue +  " na carteira.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateBudget(String transacao) {
        if(transacao.equals("compra"))
            currentUser.setBudget(currentUser.getBudget() - (result.getRegularMarketPrice() * quantity));
        else
            currentUser.setBudget(currentUser.getBudget() + (result.getRegularMarketPrice() * quantity));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = dateFormat.format(currentUser.getBirthDate());

        UserDTO userDTO = new UserDTO(currentUser.getName(), currentUser.getEmail(),
                currentUser.getPassword(), formattedDate, currentUser.getGender(), currentUser.getProfit(),
                currentUser.getBudget());

        userService.updateUser(currentUser.getId(), userDTO, new UserServiceImp.UserCallback() {
            @Override
            public void onSuccess(User user) {
                searchUser();
            }

            @Override
            public void onError(String error) {
                Log.e("UPDATE USER", error);
                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSaleButton() {

        userStockService.getUserStockByCode(currentUser.getId(), (String) getIntent().getExtras().getSerializable("code"), new UserStockServiceImpl.UserStockServiceCallback() {
            @Override
            public void onSuccess(UserStock result) {
                if(result != null) {
                    saleStock.setVisibility(View.VISIBLE);
                    stockInWallet.setVisibility(View.VISIBLE);
                    stockInWallet.setText("Quantidade na carteira: " + result.getQuantity());
                } else {
                    saleStock.setVisibility(View.GONE);
                    stockInWallet.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Erro na API: Get by code", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerBuy() {

        userStockService.getUserStockByCode(currentUser.getId(), result.getSymbol(), new UserStockServiceImpl.UserStockServiceCallback() {
            @Override
            public void onSuccess(UserStock result) {
                if(result != null) {
                    findStock(result.getId());
                } else {
                    newStock();
                    updateBudget("compra");
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Erro na API: Get by code", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void findStock(int stockId) {
        userStockService.getUserStockById(stockId, new UserStockServiceImpl.UserStockServiceCallback() {
            @Override
            public void onSuccess(UserStock result) {
                updateStock(result, "Compra");
                updateBudget("compra");
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Erro na API", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void newStock() {

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String formattedDate = outputFormat.format(new Date());

        UserStock userStock = new UserStock(0, quantity, result.getLongName(),
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
    }

    private void updateStock(UserStock userStock, String transacao) {

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String formattedDate = outputFormat.format(new Date());

        double updateAmount = userStock.getAmountValue() + (quantity * result.getRegularMarketPrice());

        UserStockDTO userStockDTO = new UserStockDTO(userStock.getId(), userStock.getQuantity() + quantity,
                userStock.getName(), userStock.getCode(), userStock.getStockType().toString(),userStock.getTransactionType().toString(),
                formattedDate, updateAmount, userStock.getUserId());

        userStockService.updateUserStock(userStockDTO.getId(), userStockDTO, new UserStockServiceImpl.UserStockServiceCallback() {
            @Override
            public void onSuccess(UserStock result) {
                if(transacao.equals("Venda"))
                    stockInWallet.setText(String.valueOf(userStock.getQuantity()));

                Toast.makeText(getApplicationContext(), transacao + " realizada com sucesso!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Erro na compra!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchUser() {

        userService.findByEmail(email, new UserServiceImp.UserCallback() {
            @Override
            public void onSuccess(User user) {
                currentUser = user;
                showSaleButton();
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
                priceVariation.setText("Variação: R$" + result.getRegularMarketChange()+ " (" + result.getRegularMarketChangePercent() + "%)");
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
        cpTotalQuantity = findViewById(R.id.cpTotalQuantity);
        ibPlus = findViewById(R.id.ibPlus);
        ibMinus = findViewById(R.id.ibMinus);
        saleStock = findViewById(R.id.btnSaleStock);
        stockInWallet = findViewById(R.id.tvStockInWallet);
    }
}