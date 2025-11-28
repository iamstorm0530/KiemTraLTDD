package com.example.midtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtest.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCate, rcProduct;

    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;

    APICategory apiCategory;
    APIMain apiMain;

    List<Category> categoryList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        GetCategory();
        GetProduct();
    }

    private void AnhXa(){
        rcCate = findViewById(R.id.rvCategories);
    }

    // ====================== CATEGORY =======================
    private void GetCategory(){
        apiCategory = RetroClientCategory.getRetrofit().create(APICategory.class);
        apiCategory.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryList = response.body();

                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);

                    rcCate.setHasFixedSize(true);
                    rcCate.setLayoutManager(
                            new LinearLayoutManager(MainActivity.this,
                                    LinearLayoutManager.HORIZONTAL, false)
                    );
                    rcCate.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("CATEGORY_ERR", t.getMessage());
            }
        });
    }

    // ====================== PRODUCT =======================
    private void GetProduct(){
        apiMain = RetroClientMain.getClient().create(APIMain.class);

        apiMain.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {

                    productList = response.body();

                    productAdapter = new ProductAdapter(MainActivity.this, productList);

                    rcProduct.setHasFixedSize(true);
                    rcProduct.setLayoutManager(
                            new LinearLayoutManager(MainActivity.this,
                                    LinearLayoutManager.HORIZONTAL, false)
                    );

                    rcProduct.setAdapter(productAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error loading products!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("PRODUCT_ERR", t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
