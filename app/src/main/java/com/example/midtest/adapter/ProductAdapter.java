package com.example.midtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.midtest.Product;
import com.example.midtest.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productDataArrayList;
    private Context context;

    public ProductAdapter(ArrayList<Product> productDataArrayList, Context mcontext) {
        this.productDataArrayList = productDataArrayList;
        this.context = mcontext;
    }

    public void updateList(ArrayList<Product> newList) {
        productDataArrayList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Set the data to textview and imageview.
        Product recyclerData = productDataArrayList.get(position);
        holder.productTV.setText(recyclerData.getName());
        holder.priceTV.setText(String.valueOf(recyclerData.getPrice())); // Convert int to String
        Glide.with(context)
                .load(recyclerData.getImage())
                .into(holder.productIV);
    }

    @Override
    public int getItemCount() {
        return productDataArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productTV;
        private TextView priceTV;
        private ImageView productIV;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productTV = itemView.findViewById(R.id.tvProduct);
            productIV = itemView.findViewById(R.id.imgProduct);
            priceTV = itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Bạn đã chọn " + productTV.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
