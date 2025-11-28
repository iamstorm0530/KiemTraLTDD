package com.example.midtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtest.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private APIMain apiService;
    private ArrayList<Product> productList;
    private boolean isSortedAscending = true;
    private Button btnSortPrice;
    private LinearLayout navHome, navProfile, navCart, navSupport, navSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        anhXa();

        String categoryId = getIntent().getStringExtra("CATEGORY_ID");
        getProduct(categoryId);

        btnSortPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortProductsByPrice();
            }
        });

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(ProductActivity.this, IntroActivity.class));
        });

        navProfile.setOnClickListener(v -> Toast.makeText(ProductActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show());
        navCart.setOnClickListener(v -> Toast.makeText(ProductActivity.this, "Cart Clicked", Toast.LENGTH_SHORT).show());
        navSupport.setOnClickListener(v -> Toast.makeText(ProductActivity.this, "Support Clicked", Toast.LENGTH_SHORT).show());
        navSettings.setOnClickListener(v -> Toast.makeText(ProductActivity.this, "Settings Clicked", Toast.LENGTH_SHORT).show());
    }

    private void anhXa() {
        recyclerView = findViewById(R.id.rvProduct);
        btnSortPrice = findViewById(R.id.btnSortPrice);
        navHome = findViewById(R.id.navHome);
        navProfile = findViewById(R.id.navProfile);
        navCart = findViewById(R.id.navCart);
        navSupport = findViewById(R.id.navSupport);
        navSettings = findViewById(R.id.navSettings);
    }

    private void getProduct(String categoryId) {
        apiService = RetroClientProduct.getClient().create(APIMain.class);

        Call<List<Product>> call;
        if (categoryId != null && !categoryId.isEmpty()) {
            call = apiService.getProductByCategory(categoryId);
        } else {
            call = apiService.getAllProducts();
        }

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    generateDataList(response.body());
                } else {
                    Toast.makeText(ProductActivity.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Product> productList) {
        if (productList != null) {
            this.productList = new ArrayList<>(productList);
            productAdapter = new ProductAdapter(this.productList, this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(productAdapter);
        }
    }

    private void sortProductsByPrice() {
        if (productList != null && !productList.isEmpty()) {
            if (isSortedAscending) {
                Collections.sort(productList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Integer.compare(p1.getPrice(), p2.getPrice());
                    }
                });
                Toast.makeText(this, "Sắp xếp giá tăng dần", Toast.LENGTH_SHORT).show();
            } else {
                Collections.sort(productList, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Integer.compare(p2.getPrice(), p1.getPrice());
                    }
                });
                Toast.makeText(this, "Sắp xếp giá giảm dần", Toast.LENGTH_SHORT).show();
            }
            isSortedAscending = !isSortedAscending; // Toggle the sort order
            productAdapter.updateList(productList);
        }
    }
}
