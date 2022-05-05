package com.example.solofly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignInEmail extends AppCompatActivity {

    TextView changeToNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_email);

        changeToNumber = findViewById(R.id.changeToNumber);

        changeToNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent=new Intent(SignInEmail.this, SignIn.class);
                startActivity(emailIntent);
                finish();
            }
        });
    }
}