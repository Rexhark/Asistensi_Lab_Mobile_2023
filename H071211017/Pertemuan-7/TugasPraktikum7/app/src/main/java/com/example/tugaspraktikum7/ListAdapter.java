package com.example.tugaspraktikum7;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final List<UserResponse> listAdapter;

    public ListAdapter(List<UserResponse> listAdapter) {
        this.listAdapter = listAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserResponse userResponse = listAdapter.get(position);
        Intent profileActivity = new Intent(holder.itemView.getContext(), ProfileActivity.class);
        String fullName = userResponse.getFirstName() + " " +
                userResponse.getLastName();
        String urlAvatar = userResponse.getAvatarUrl();
        int id = userResponse.getId();
        holder.tv_name.setText(fullName);
        holder.tv_email.setText(userResponse.getEmail());
        Glide.with(holder.itemView.getContext())
                .load(urlAvatar)
                .centerCrop()
                .into(holder.iv_avatar);
        holder.container.setOnClickListener(v -> {
            profileActivity.putExtra("id", id);
            holder.itemView.getContext().startActivity(profileActivity);
        });
    }

    @Override
    public int getItemCount() {
        return listAdapter.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat container;
        ImageView iv_avatar;
        TextView tv_name, tv_email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_email = itemView.findViewById(R.id.tv_email);
        }
    }

}
