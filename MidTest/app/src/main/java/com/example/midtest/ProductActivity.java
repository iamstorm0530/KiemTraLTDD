package com.example.midtest;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    RecyclerView rv;
    ProductAdapter adapter;
    List<Product> list = new ArrayList<>();
    APIMain api;

    String cateId = "";
    String cateName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rv = findViewById(R.id.rvProduct);
        TextView tvTitle = findViewById(R.id.tvTitle);

        cateId = getIntent().getStringExtra("cateId");
        cateName = getIntent().getStringExtra("cateName");
        tvTitle.setText(cateName);

        api = RetroClientMain.getClient().create(APIMain.class);

        rv.setLayoutManager(new GridLayoutManager(this, 2));

        loadProducts();
    }

    private void loadProducts() {
        api.getProductByCategory(cateId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> res) {
                if (res.isSuccessful()) {
                    list = res.body();

                    // sort theo price tăng dần
                    Collections.sort(list, Comparator.comparingInt(Product::getPrice));

                    adapter = new ProductAdapter(ProductActivity.this, list);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Load fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

