package com.example.oilandgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();
    SqliteHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setInitialData();
        System.out.println("proddddd " + products.size());
        RecyclerView recyclerView = findViewById(R.id.cart);
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(products, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void setInitialData(){
        databaseHelper = new SqliteHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        userCursor =  db.rawQuery("SELECT product.* FROM product INNER JOIN cart ON cart.product == product.id", null);

        while(userCursor.moveToNext()) {
            System.out.println("1111111");
            products.add(new Product(
                    userCursor.getString(userCursor.getColumnIndexOrThrow("name")),
                    userCursor.getString(userCursor.getColumnIndexOrThrow("price")) + "$",
                    userCursor.getInt(userCursor.getColumnIndexOrThrow("image")),
                    userCursor.getString(userCursor.getColumnIndexOrThrow("id"))
            ));
            System.out.println(products.get(0).getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.product:
                Intent intent = new Intent(this, ProductActivity.class);
                startActivity(intent);
                return true;
            case R.id.cart:
                intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}