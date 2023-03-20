package com.example.oilandgas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UsernameDialog extends AppCompatDialogFragment {

    EditText username = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.username_dialog, null);
        username = view.findViewById(R.id.username_dialog);

        builder.setView(view)
                .setTitle("Change username")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AAA",getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        username = view.findViewById(R.id.username_dialog);
                        String usernameString = username.getText().toString();
                        Toast.makeText(getContext(), usernameString, Toast.LENGTH_SHORT).show();
                        myEdit.putString("username", usernameString);
                        myEdit.commit();
                        ((TextView)getActivity().findViewById(R.id.settings_username)).setText(sharedPreferences.getString("username", "username"));
                    }
                });
        return builder.create();
    }
}
