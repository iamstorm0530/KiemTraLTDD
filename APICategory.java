package com.example.midtest;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import com.example.midtest.Category;

public interface APICategory {
    @GET("category")
    Call<List<Category>> getCategoryAll();
}