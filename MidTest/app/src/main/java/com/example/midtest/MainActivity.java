package com.example.midtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // CATEGORY
    RecyclerView rvCategories;
    CategoryAdapter categoryAdapter;
    APICategory apiCategory;
    TextView tvUserName;

    LinearLayout navHome, navProfile, navCart, navSupport, navSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadUserInfo();
        loadCategories();

    }
    private void initViews() {
        rvCategories = findViewById(R.id.rvCategories);
        tvUserName = findViewById(R.id.tvUserName);

        navHome = findViewById(R.id.navHome);
        navProfile = findViewById(R.id.navProfile);
        navCart = findViewById(R.id.navCart);
        navSupport = findViewById(R.id.navSupport);
        navSettings = findViewById(R.id.navSettings);
    }

    private void loadUserInfo() {
        SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String name = prefs.getString("NAME", "User");
        tvUserName.setText(name);
    }

    private void loadCategories() {

        apiCategory = RetroClientCategory.getRetrofit().create(APICategory.class);

        apiCategory.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("API_CATEGORY", "FAILED OR EMPTY");
                    return;
                }
                List<Category> list = response.body();

                categoryAdapter = new CategoryAdapter(MainActivity.this, list, category -> {

                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    intent.putExtra("categoryId", category.getId());
                    startActivity(intent);
                });

                rvCategories.setHasFixedSize(true);
                rvCategories.setLayoutManager(
                        new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
                );
                rvCategories.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("API_CATEGORY_ERROR", t.getMessage());
            }
        });
    }

}
