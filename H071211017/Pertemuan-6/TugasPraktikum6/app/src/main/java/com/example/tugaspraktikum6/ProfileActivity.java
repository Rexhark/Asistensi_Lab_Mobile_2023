package com.example.tugaspraktikum6;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugaspraktikum6.databinding.ActivityProfileBinding;

import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("nameKey");
        String username = getIntent().getStringExtra("usernameKey");
        Uri uriImage = Uri.parse(getIntent().getStringExtra("imageUriKey"));

        binding.name.setText(name);
        binding.userName.setText(username);
        binding.profile.setImageURI(uriImage);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.mainLayout.setVisibility(View.GONE);

        loadingScreen();
    }

    private void loadingScreen(){
        Random rand = new Random();
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(rand.nextInt(2000));
                handler.post(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.mainLayout.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}