package com.example.oilandgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 100;

    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView image;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sh = getSharedPreferences("AAA", Context.MODE_PRIVATE);
        String username = sh.getString("username", "username");
        ((TextView) findViewById(R.id.settings_username)).setText(sh.getString("username", "username"));
        ((TextView) findViewById(R.id.settings_password)).setText(sh.getString("password", "password"));
        ((TextView) findViewById(R.id.settings_email)).setText(sh.getString("email", "aaa@bbb.ccc"));

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        image = findViewById(R.id.settings_image);
        Glide.with(getApplicationContext())
                .load("https://firebasestorage.googleapis.com/v0/b/suniy-intelekt.appspot.com/o/image.jpg?alt=media&token=2eef1bd4-d7e5-4381-bf67-9ca3cdcaae54")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (IOException e) {
                return;
            }
            image.setImageBitmap(bitmap);

            if (bitmap != null) {

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("image.jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();
                UploadTask uploadTask = ref.putBytes(imageData);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Image Uploaded!!", Toast.LENGTH_LONG).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                                System.out.println(e.getMessage());
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
        }
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