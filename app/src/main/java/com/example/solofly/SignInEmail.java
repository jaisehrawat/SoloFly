package com.example.solofly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInEmail extends AppCompatActivity {

    TextView changeToNumber;
    EditText enterEmail;
    Button signInByEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_email);

        changeToNumber = findViewById(R.id.changeToNumber);
        enterEmail = findViewById(R.id.enterEmail);
        signInByEmailBtn = findViewById(R.id.signInByEmailBtn);

        changeToNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent=new Intent(SignInEmail.this, SignIn.class);
                startActivity(emailIntent);
                finish();
            }
        });

        signInByEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enterEmail.getText().toString().trim().isEmpty() && validateEmailAddress(enterEmail))
                {
                    Intent emailOtpVerification=new Intent(SignInEmail.this, OTP.class);
                    emailOtpVerification.putExtra("email", enterEmail.getText().toString());
                    startActivity(emailOtpVerification);
                }
                else
                {
                    Toast.makeText(SignInEmail.this, "Enter a valid Email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validateEmailAddress(EditText enterEmail) {
        String email=enterEmail.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}