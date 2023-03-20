package com.example.oilandgas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sh = getSharedPreferences("AAA", Context.MODE_PRIVATE);
        String username = sh.getString("username", "username");
        ((TextView)findViewById(R.id.settings_username)).setText(sh.getString("username", "username"));
        ((TextView)findViewById(R.id.settings_password)).setText(sh.getString("password", "password"));
        ((TextView)findViewById(R.id.settings_email)).setText(sh.getString("email", "aaa@bbb.ccc"));
    }

    public void username_submit(View view) {
        UsernameDialog exampleDialog = new UsernameDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void password_submit(View view) {
        PasswordDialog exampleDialog = new PasswordDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void email_submit(View view) {
        EmailDialog exampleDialog = new EmailDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
}