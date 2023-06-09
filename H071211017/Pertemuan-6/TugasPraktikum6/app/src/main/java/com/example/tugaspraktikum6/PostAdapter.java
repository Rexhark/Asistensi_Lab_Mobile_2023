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

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private final List<String[]> items;
    private final Context context;

    public PostAdapter(Context context, List<String[]> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String username = items.get(position)[0];
        String name = items.get(position)[1];
        Uri uriProfil = Uri.parse(items.get(position)[2]);
        Uri uriImage = Uri.parse(items.get(position)[3]);
        String caption = items.get(position)[4];

        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("nameKey",name);
        intent.putExtra("usernameKey",username);
        intent.putExtra("imageUriKey", String.valueOf(uriProfil));

        Glide.with(context)
                .load(uriProfil)
                .centerCrop()
                .into(holder.profil);

        Glide.with(context)
                .load(uriImage)
                .centerCrop()
                .into(holder.image);

        holder.name.setText(name);
        holder.username.setText(username);
        holder.caption.setText(caption);
        holder.profil.setOnClickListener(v -> context.startActivity(intent));
        holder.namaUser.setOnClickListener(v -> context.startActivity(intent));

    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView caption, name, username;
        ShapeableImageView profil;
        LinearLayout namaUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            image = itemView.findViewById(R.id.gambar);
            caption = itemView.findViewById(R.id.caption);
            profil = itemView.findViewById(R.id.profilUser);
            namaUser = itemView.findViewById(R.id.namaUnameUser);
        }
    }
}
