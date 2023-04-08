package com.example.oilandgas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.RecyclerViewHolder>{
    private ArrayList<Product> courseDataArrayList;
    private Context mcontext;

    public ProductRecyclerViewAdapter(ArrayList<Product> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public ProductRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        Product recyclerData = courseDataArrayList.get(position);
        holder.product_name.setText(recyclerData.getName());
        holder.product_image.setImageResource(recyclerData.getImage());
        holder.product_price.setText(recyclerData.getPrice());
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView product_image;
        private TextView product_name;
        private TextView product_price;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product product = courseDataArrayList.get(this.getAdapterPosition());
            Intent i = new Intent(mcontext.getApplicationContext(), SingleProductActivity.class);
            i.putExtra("name", product.getName());
            i.putExtra("price",product.getPrice());
            i.putExtra("image", product.getImage());
            i.putExtra("id", product.getId());
            mcontext.startActivity(i);
        }

    }
}
