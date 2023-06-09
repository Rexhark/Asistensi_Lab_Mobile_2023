package com.example.tugaspraktikum7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ShapeableImageView ivAvatar;
    TextView tvName, tvEmail;
    MaterialCardView container;
    ProgressBar progressBar;
    LinearLayoutCompat containerReconnect;
    ImageView ivRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivAvatar = findViewById(R.id.iv_avatar);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        container = findViewById(R.id.container);
        progressBar = findViewById(R.id.progressBar);
        containerReconnect = findViewById(R.id.container_reconnect);
        ivRefresh = findViewById(R.id.iv_refresh);

        int idData = getIntent().getIntExtra("id",0);


        if (!isNetworkConnected()){
            progressBar.setVisibility(View.GONE);
            container.setVisibility(View.GONE);
            containerReconnect.setVisibility(View.VISIBLE);
            ivRefresh.setOnClickListener(v -> {
                containerReconnect.setVisibility(View.GONE);
                container.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            });
        } else {
            containerReconnect.setVisibility(View.GONE);
            container.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            Call<DataResponse> client = ApiConfig.getApiService().getSpesificUser(String.valueOf(idData));
            client.enqueue(new Callback<DataResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<DataResponse> call, @NonNull Response<DataResponse> response) {
                    assert response.body() != null;
                    if (response.isSuccessful()) {
                        UserResponse userResponse = response.body().getData();
                        String fullName = userResponse.getFirstName() + " " +
                                userResponse.getLastName();
                        Glide.with(ProfileActivity.this)
                                .load(userResponse.getAvatarUrl())
                                .centerCrop()
                                .into(ivAvatar);
                        tvName.setText(fullName);
                        tvEmail.setText(userResponse.getEmail());
                        progressBar.setVisibility(View.GONE);
                        containerReconnect.setVisibility(View.GONE);
                        container.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<DataResponse> call, @NonNull Throwable t) {
                    Log.e("ProfileActivity", "onFailure: " + t.getMessage());
                }
            });
        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}