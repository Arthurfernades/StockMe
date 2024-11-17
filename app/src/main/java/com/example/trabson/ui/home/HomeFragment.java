package com.example.trabson.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabson.R;
import com.example.trabson.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private TextView userName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        userName = getActivity().findViewById(R.id.text_home);

        userName.setText(getArguments().getString("nome"));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}