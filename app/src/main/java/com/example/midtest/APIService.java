package com.example.midtest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface APIService {
    @GET("user")
    Call<List<User>> getUsers();

    @POST("user")
    Call<User> registerUser(@Body User user);

}
