package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {

    TextInputEditText nameEdittext, phoneEdittext, emailEdittext, dateOfBirthEdittext;
    String name, email, mobile, dob, profileUrl;
    ImageView profilePhoto, back_button;
    Button logout;
    String phone;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");

        nameEdittext = findViewById(R.id.nameEdittext);
        phoneEdittext = findViewById(R.id.phoneEdittext);
        emailEdittext = findViewById(R.id.emailEdittext);
        dateOfBirthEdittext = findViewById(R.id.dateOfBirthEdittext);
        profilePhoto = findViewById(R.id.profilePhoto);
        back_button = findViewById(R.id.back_button);
        logout = findViewById(R.id.logout);

        reference = FirebaseDatabase.getInstance().getReference("User").child(phone);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Profile.this);
                builder.setTitle("Alert");
                builder.setMessage("Do you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Profile.this, SignIn.class);
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
                    name = d.getName();
                    email = d.getEmail();
                    mobile = d.getPhone();
                    dob = d.getDate_of_birth();
                    profileUrl = d.getProfileUrl();
                    nameEdittext.setText(name);
                    emailEdittext.setText(email);
                    phoneEdittext.setText(mobile);
                    dateOfBirthEdittext.setText(dob);
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
                Profile.super.onBackPressed();
            }
        });
    }
}