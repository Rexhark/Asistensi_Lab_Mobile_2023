package com.example.tugaspraktikum7;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataListResponse {
    @SerializedName("data")
    private List<UserResponse> data;
    public List<UserResponse> getData() {
        return data;
    }
}
