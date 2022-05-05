package com.example.solofly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {

    TextView changeToEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        changeToEmail = findViewById(R.id.changeToEmail);

        changeToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent numberIntent=new Intent(SignIn.this, SignInEmail.class);
                startActivity(numberIntent);
                finish();
            }
        });
    }

}