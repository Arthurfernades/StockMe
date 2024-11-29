package com.example.trabson.ui.wallet;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabson.R;
import com.example.trabson.adapter.stocks.StockAdapter;
import com.example.trabson.adapter.userStocks.WalletStockAdapter;
import com.example.trabson.helper.SvgLoader;
import com.example.trabson.model.Result;
import com.example.trabson.model.User;
import com.example.trabson.model.UserStock;
import com.example.trabson.model.dto.StockWalletRecycleViewDTO;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.model.dto.UserStockDTO;
import com.example.trabson.service.stock.IStockService;
import com.example.trabson.service.stock.StockServiceImpl;
import com.example.trabson.service.user.IUserService;
import com.example.trabson.service.user.UserServiceImp;
import com.example.trabson.service.userStock.UserStockServiceImpl;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WalletFragment extends Fragment {

    private TextView tvBudget, tvProfit;

    private View view;

    private Button btnAddBudget, btnWithdrawMoney;

    private User currentUser;

    private UserServiceImp userService;

    private UserStockServiceImpl userStockService;

    private StockServiceImpl stockService;

    private List<UserStockDTO>  userStockList;

    private double totalPurchased, currentTotal;

    private List<StockWalletRecycleViewDTO> stockRv;

    private RecyclerView recyclerView;

    private WalletStockAdapter stockAdapter;

    private List<StockWalletRecycleViewDTO> stockWalletRecycleViewList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wallet, container, false);

        userService = new UserServiceImp();

        userStockService = new UserStockServiceImpl();

        stockService = new StockServiceImpl();

        binding();

        recyclerView = view.findViewById(R.id.rvUserStocks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

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

    public void binding() {
        tvBudget = view.findViewById(R.id.tvBudget);
        tvProfit = view.findViewById(R.id.tvProfit);
        btnAddBudget = view.findViewById(R.id.btnAddBudget);
        btnWithdrawMoney = view.findViewById(R.id.btnWithdrawMoney);
    }

    @Override
    public void onStart() {
        super.onStart();

        findUser(getArguments().getString("email"));

        btnAddBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBudgetDialog("Deposit");
            }
        });

        btnWithdrawMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBudgetDialog("Withdraw");
            }
        });
    }

    public void findUser(String email) {

        userService.findByEmail(email, new UserServiceImp.UserCallback() {
            @Override
            public void onSuccess(User user) {
                currentUser = user;
                tvBudget.setText("Saldo: R$" + currentUser.getBudget());
                if( currentUser.getProfit() < 0) {
                    tvProfit.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tvProfit.setTextColor(getResources().getColor(R.color.green));
                }
                tvProfit.setText("Rentabilidade: R$" + currentUser.getProfit());
                findUserStocks();
            }

            @Override
            public void onError(String error) {
                Log.e("API USER", "Erro ao buscar o E-mail do usuário!");
            }
        });
    }

    public void findUserStocks() {

        userStockList = null;

        userStockService.getUserStockByUserId(currentUser.getId(), new UserStockServiceImpl.UserStockServiceListCallback() {
            @Override
            public void onSuccess(List<UserStockDTO> result) {
                userStockList = result;
                findStocksCurrentPrice(result);
            }

            @Override
            public void onError(String error) {
                Log.e("API USER", error);
            }
        });
    }

    public void totalBuyPrice(Double currentTotal) {
        for(UserStockDTO userStock : userStockList) {
            totalPurchased += userStock.getAmountValue();
        }
        tvProfit.setText("+" + (totalPurchased - currentTotal));
    }

    public void updateRecycleView(List<StockWalletRecycleViewDTO> stockWalletRecycleViewList) {
        stockAdapter = new WalletStockAdapter(stockWalletRecycleViewList, viewStockInfo);
        recyclerView.setAdapter(stockAdapter);
//        stockAdapter.updateData(stockWalletRecycleViewList);
    }

    private void findStocksCurrentPrice(List<UserStockDTO> userStockList) {

        stockWalletRecycleViewList = new ArrayList<>();
        currentTotal = 0;
        AtomicInteger pendingRequests = new AtomicInteger(userStockList.size());

        for (UserStockDTO userStockDTO : userStockList) {

            StockWalletRecycleViewDTO stockWallet = new StockWalletRecycleViewDTO();
            stockWallet.setCode(userStockDTO.getCode());
            stockWallet.setAmuntCurrentPrice(userStockDTO.getAmountValue());
            stockWallet.setQuantity(userStockDTO.getQuantity());

            stockService.getStockInfo(userStockDTO.getCode(), new StockServiceImpl.StockInfoCallback() {
                @Override
                public void onSuccess(List<Result> results) {
                    Result result = results.get(0);
                    stockWallet.setSvgUrl(result.getLogoUrl());
                    stockWallet.setAmuntCurrentPrice(result.getRegularMarketPrice());
                    synchronized (stockWalletRecycleViewList) {
                        stockWalletRecycleViewList.add(stockWallet);
                    }
                    currentTotal += (result.getRegularMarketPrice() * userStockDTO.getQuantity());

                    if (pendingRequests.decrementAndGet() == 0) {
                        totalBuyPrice(currentTotal);
                        updateRecycleView(stockWalletRecycleViewList);
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("USER STOCK API", error);

                    if (pendingRequests.decrementAndGet() == 0) {
                        totalBuyPrice(currentTotal);
                        updateRecycleView(stockWalletRecycleViewList);
                    }
                }
            });
        }
    }


    public void showBudgetDialog(String transaction) {

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_budget);
        dialog.setCancelable(true);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }

        TextInputLayout cpAddBudget = dialog.findViewById(R.id.cpAddBudget);
        Button btnCancelBudget = dialog.findViewById(R.id.btnCancelBudget);
        Button btnConfirmBudget = dialog.findViewById(R.id.btnConfirmBudget);

        cpAddBudget.getEditText().setText("0.0");

        btnCancelBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirmBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double valorSaldo = Double.parseDouble(cpAddBudget.getEditText().getText().toString());

                if (valorSaldo > 0 && transaction.equals("Deposit")) {
                    currentUser.setBudget(currentUser.getBudget() + valorSaldo);
                } else if(valorSaldo < currentUser.getBudget() && transaction.equals("Withdraw"))
                        currentUser.setBudget(currentUser.getBudget() - valorSaldo);
                else {
                    Toast.makeText(getContext(), "Por favor, insira um valor válido", Toast.LENGTH_SHORT).show();
                    return;
                }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String formattedDate = dateFormat.format(currentUser.getBirthDate());

                    UserDTO userDTO = new UserDTO(currentUser.getName(), currentUser.getEmail(),
                            currentUser.getPassword(), formattedDate, currentUser.getGender(), currentUser.getProfit(),
                            currentUser.getBudget());

                    userService.updateUser(currentUser.getId(), userDTO, new UserServiceImp.UserCallback() {
                        @Override
                        public void onSuccess(User user) {
                            Toast.makeText(getContext(), "Saldo adicionado: R$ " + valorSaldo, Toast.LENGTH_SHORT).show();
                            findUser(currentUser.getEmail());
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("UPDATE USER", error);
                            Toast.makeText(getContext(), "Não foi possível adicionar o saldo, tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}