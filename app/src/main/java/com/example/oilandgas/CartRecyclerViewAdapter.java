package com.example.oilandgas;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<Product> courseDataArrayList;
    private Context mcontext;

    private SqliteHelper databaseHelper;
    private SQLiteDatabase db;

    public CartRecyclerViewAdapter(ArrayList<Product> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        databaseHelper = new SqliteHelper(mcontext.getApplicationContext());
        db = databaseHelper.getReadableDatabase();
    }
    @NonNull
    @Override
    public CartRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_main, parent, false);
        return new CartRecyclerViewAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        Product recyclerData = courseDataArrayList.get(position);
        holder.product_id = courseDataArrayList.get(position).getId();
        holder.product_name.setText(recyclerData.getName());
        holder.product_image.setImageResource(recyclerData.getImage());
        holder.product_price.setText(recyclerData.getPrice());
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_image;
        private TextView product_name;
        private TextView product_price;

        private String product_id;

        private Button btn_buy;
        private Button btn_remove;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.cart_image);
            product_name = itemView.findViewById(R.id.cart_description);
            product_price = itemView.findViewById(R.id.cart_price);
            btn_buy = itemView.findViewById(R.id.cart_buy);
            btn_remove = itemView.findViewById(R.id.cart_remove);

            btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mcontext, "Thank you to buy this product", Toast.LENGTH_SHORT).show();
                    try {
                        db.execSQL(
                                String.format("DELETE FROM cart WHERE product = '%s'", product_id)
                        );
                        mcontext.startActivity(new Intent(mcontext, CartActivity.class));
                    } catch (SQLException e) {
                        Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        db.execSQL(
                                String.format("DELETE FROM cart WHERE product = '%s'", product_id)
                        );
                        mcontext.startActivity(new Intent(mcontext, CartActivity.class));
                    } catch (SQLException e) {
                        Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
