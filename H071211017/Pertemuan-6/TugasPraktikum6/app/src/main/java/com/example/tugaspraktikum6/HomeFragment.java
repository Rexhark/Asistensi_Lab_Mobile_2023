package com.example.tugaspraktikum6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    List<String[]> items = new ArrayList<>();
    RecyclerView rvPost;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        items = ((MainActivity) requireActivity()).getItems();

        PostAdapter adapter = new PostAdapter(getContext(),items);
        progressBar = view.findViewById(R.id.progressBar);

        // Add the following lines to create RecyclerView
        rvPost = view.findViewById(R.id.rvPost);
        rvPost.setHasFixedSize(true);
        rvPost.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPost.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        rvPost.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingScreen();
    }

    private void loadingScreen(){
        Random rand = new Random();
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(rand.nextInt(2000));
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    rvPost.setVisibility(View.VISIBLE);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}