package com.example.solofly;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class settings extends AppCompatActivity {

    ExtendedFloatingActionButton logout;
    String phone, name, profileUrl;
    ImageView profilePhoto, back_button;
    TextView nameView;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");
        reference = FirebaseDatabase.getInstance().getReference("User").child(phone);

        logout = findViewById(R.id.logout);
        profilePhoto = findViewById(R.id.settings_profile_photo);
        back_button = findViewById(R.id.settings_back_button);
        nameView = findViewById(R.id.settings_profile_name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(settings.this);
                builder.setTitle("Alert");
                builder.setMessage("Do you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(settings.this, SignIn.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = preferences.edit();
                        myEdit.putBoolean("isLoggedIn", false);
                        myEdit.apply();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Data d = snapshot.getValue(Data.class);
                    profileUrl = d.getProfileUrl();
                    name = d.getName();
                    nameView.setText(name);
                    Picasso.get().load(profileUrl).into(profilePhoto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.super.onBackPressed();
            }
        });
    }
}