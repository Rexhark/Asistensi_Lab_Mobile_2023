package com.example.tugaspraktikum6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchFragment extends Fragment {
    List<String[]> accounts = new ArrayList<>();
    RecyclerView rvAccount;
    ProgressBar progressBar;
    EditText etSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        accounts = ((MainActivity) requireActivity()).getAccounts();

        AccountsAdapter adapter = new AccountsAdapter(getContext(),accounts);
        progressBar = view.findViewById(R.id.progressBar);
        etSearch = view.findViewById(R.id.etSearch);

        // Add the following lines to create RecyclerView
        rvAccount = view.findViewById(R.id.rvAccount);
        rvAccount.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        rvAccount.setVisibility(View.GONE);
        loadingScreen();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                progressBar.setVisibility(View.VISIBLE);
                rvAccount.setVisibility(View.GONE);
                loadingScreen();
                filterAccounts(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadingScreen(){
        Random rand = new Random();
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(rand.nextInt(1000));
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    rvAccount.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void filterAccounts(String searchText){
        List<String[]> filteredAccounts = new ArrayList<>();
        for (String[] account : accounts){
            if (account[0].toLowerCase().contains(searchText.toLowerCase()) || account[1].toLowerCase().contains(searchText.toLowerCase())){
                filteredAccounts.add(account);
            }
        }
        AccountsAdapter adapter = new AccountsAdapter(getContext(),filteredAccounts);
        rvAccount.setAdapter(adapter);
    }

}