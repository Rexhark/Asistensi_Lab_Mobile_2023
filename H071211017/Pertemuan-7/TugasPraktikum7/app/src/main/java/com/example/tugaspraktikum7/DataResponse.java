package com.example.tugaspraktikum7;

import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName("data")
    private UserResponse data;
    public UserResponse getData() {
        return data;
    }

}
