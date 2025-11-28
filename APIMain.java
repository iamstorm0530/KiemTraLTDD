package com.example.midtest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface APIMain {
    @GET("user")
    Call<List<User>> getUsers();
    @POST("user")
    Call<User> registerUser(@Body User user);
    @GET("product")
    Call<List<Product>> getAllProducts();
    @GET("product")
    Call<List<Product>> getProductByCategory(@Query("categoryId") String categoryId);

}
