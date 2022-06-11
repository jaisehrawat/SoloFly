package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class appbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar);
    }
}


//    ImageView profile_icon, back_button;
//    TextView activity_name;
//    String profileUrl, phone;


//        back_button=findViewById(R.id.back_button);
//        activity_name=findViewById(R.id.activity_name);
//        profile_icon=findViewById(R.id.profile_icon);


//    protected void appbarFunctionality(){
//        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
//        phone = preferences.getString("phone", "");
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(phone);
//
////        Back button
//        back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                settings.super.onBackPressed();
//            }
//        });
//
////      Activity name set
//        activity_name.setText("@Activity_name");
//
////      Profile icon set
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    Data d = snapshot.getValue(Data.class);
//                    profileUrl = d.getProfileUrl();
//                    Picasso.get().load(profileUrl).into(profile_icon);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//profile_icon.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        Intent intent = new Intent(getApplicationContext(), Profile.class);
//        startActivity(intent);
//        }
//        });
//    }
