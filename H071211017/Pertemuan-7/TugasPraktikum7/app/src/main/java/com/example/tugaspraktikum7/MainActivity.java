package com.example.tugaspraktikum7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvData;
    List<UserResponse> userResponseList = new ArrayList<>();
    ListAdapter listAdapter;
    ProgressBar progressBar;
    LinearLayoutCompat container;
    ImageView ivRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvData = findViewById(R.id.rvData);
        progressBar = findViewById(R.id.progressBar);
        container = findViewById(R.id.container);
        ivRefresh = findViewById(R.id.iv_refresh);

        if (!isNetworkConnected()) {
            progressBar.setVisibility(View.GONE);
            rvData.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            ivRefresh.setOnClickListener(v -> {
                container.setVisibility(View.GONE);
                rvData.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            });
        } else {
            container.setVisibility(View.GONE);
            rvData.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Call<DataListResponse> client = ApiConfig.getApiService().getUser("1");
            client.enqueue(new Callback<DataListResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<DataListResponse> call, @NonNull Response<DataListResponse> response) {
                    assert response.body() != null;
                    userResponseList.addAll(response.body().getData());
                    listAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<DataListResponse> call, @NonNull Throwable t) {
                    Log.e("MainActivity", "onFailure: " + t.getMessage());
                }
            });

            client = ApiConfig.getApiService().getUser("2");
            client.enqueue(new Callback<DataListResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<DataListResponse> call, @NonNull Response<DataListResponse> response) {
                    assert response.body() != null;
                    userResponseList.addAll(response.body().getData());
                    listAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    container.setVisibility(View.GONE);
                    rvData.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<DataListResponse> call, @NonNull Throwable t) {
                    Log.e("MainActivity", "onFailure: " + t.getMessage());
                }
            });

            listAdapter = new ListAdapter(userResponseList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvData.setLayoutManager(layoutManager);
            rvData.setAdapter(listAdapter);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}