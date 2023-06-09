package com.example.tugaspraktikum6;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    private final List<String[]> items;
    private final Context context;

    public AccountsAdapter(Context context, List<String[]> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public AccountsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_account, parent, false);
        return new AccountsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsAdapter.ViewHolder holder, int position) {

        System.out.println("Success");

        String username = items.get(position)[0];
        String name = items.get(position)[1];
        Uri uriProfil = Uri.parse(items.get(position)[2]);

        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("nameKey",name);
        intent.putExtra("usernameKey",username);
        intent.putExtra("imageUriKey", String.valueOf(uriProfil));

        Glide.with(context)
                .load(uriProfil)
                .centerCrop()
                .into(holder.profilUser);

        holder.name.setText(name);
        holder.userName.setText(username);
        holder.profilUser.setOnClickListener(v -> context.startActivity(intent));
        holder.namaUnameUser.setOnClickListener(v -> context.startActivity(intent));

    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profilUser;
        TextView name, userName;
        LinearLayout namaUnameUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilUser = itemView.findViewById(R.id.profilUser);
            name = itemView.findViewById(R.id.name);
            userName = itemView.findViewById(R.id.userName);
            namaUnameUser = itemView.findViewById(R.id.namaUnameUser);
        }
    }

}
