package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity {

    EditText enterNumber;
    Button signInByNumber;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        enterNumber=(EditText)findViewById(R.id.enterNumber);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(enterNumber);
        signInByNumber=(Button)findViewById(R.id.signInByNumber);

        signInByNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,OTP.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
                finish();
            }
        });
    }

    }
