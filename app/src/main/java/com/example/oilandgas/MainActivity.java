package com.example.oilandgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submit(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        SharedPreferences sh = getSharedPreferences("AAA", Context.MODE_PRIVATE);
        String usernameValue = sh.getString("username", "username");
        String passwordValue = sh.getString("password", "password");
        if (usernameValue.equals(username.getText().toString()) && passwordValue.equals(password.getText().toString())) {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
        }
    }
}