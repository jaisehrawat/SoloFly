package com.example.solofly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(splashScreen.this, SignIn.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
                Boolean isLoggedInCheck = preferences.getBoolean("isLoggedIn", false);

                if (isLoggedInCheck) {
                    Intent intent = new Intent(splashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(splashScreen.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}