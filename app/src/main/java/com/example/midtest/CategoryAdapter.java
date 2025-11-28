package com.example.midtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> array;
    public CategoryAdapter(Context context, List<Category> array){
        this.context = context;
        this.array = array;
    }
    @org.jspecify.annotations.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@org.jspecify.annotations.NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tenSp;
        public MyViewHolder(@org.jspecify.annotations.NonNull View itemView){
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_cate);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameCategory);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(context,"Bạn đã chọn category" + tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        Category category = array.get(position);
        holder.tenSp.setText(category.getName());
        Glide.with(context)
                .load(category.getImage())
                .into(holder.images);
    }
    @Override
    public int getItemCount(){return array == null ? 0 :array.size();}
}
