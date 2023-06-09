package com.example.tugaspraktikum7;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/users/{id}")
    Call<DataResponse> getSpesificUser(@Path("id") String id);
    @GET("api/users")
    Call<DataListResponse> getUser(@Query("page") String page);
}
