package com.example.trabson.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabson.R;

public class WalletFragment extends Fragment {

    private TextView budget, profit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        double budgetValue = getArguments().getDouble("budget");
        double profitValue = getArguments().getDouble("profit");

        budget = view.findViewById(R.id.tvBudget);
        profit = view.findViewById(R.id.tvProfit);

        budget.setText("Saldo na carteira: " + budgetValue);
        profit.setText("Rentabilidade: " + profitValue);

        return view;
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