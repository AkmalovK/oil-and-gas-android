package com.example.oilandgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleProductActivity extends AppCompatActivity {
    private Product product;
    private Button btn;
    SqliteHelper databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        Intent intent = getIntent();

        ImageView image = findViewById(R.id.single_product_image);
        TextView name = findViewById(R.id.single_product_name);
        TextView price = findViewById(R.id.single_product_price);
        btn = findViewById(R.id.submit_cart);
        boolean isAdded = false;

        try {
            // connect with database
            databaseHelper = new SqliteHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            product = new Product(
                    intent.getStringExtra("name"),
                    intent.getStringExtra("price"),
                    intent.getIntExtra("image", R.drawable.oil),
                    intent.getStringExtra("id"));

            Cursor cursor = db.rawQuery(
                    String.format("SELECT COUNT(*) as count FROM cart WHERE product = '%s';", product.getId()), null
            );
            System.out.println(String.format("SELECT COUNT(*) as count FROM cart WHERE product = '%s';", product.getId()));
            System.out.println(product.getId());
            cursor.moveToNext();
            isAdded = cursor.getInt(cursor.getColumnIndexOrThrow("count")) > 0;
            System.out.println("aaaa " + cursor.getInt(cursor.getColumnIndexOrThrow("count")));
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (isAdded) {
            System.out.println("Added");
            btn.setText("Added");
            btn.setEnabled(false);
        }
        image.setImageResource(product.getImage());
        name.setText(product.getName());
        price.setText(product.getPrice());


    }

    public void addToCart(View view) {
        try {
            db.execSQL(
                    String.format("INSERT INTO cart(product) VALUES('%s');", product.getId())
            );
            btn.setText("Added");
            btn.setEnabled(false);
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}