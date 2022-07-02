package com.example.solofly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solofly.Adapter.DiaryAdapter;
import com.example.solofly.Model.DiaryModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Diary extends AppCompatActivity {

    //  appbar
    ImageView profile_icon, back_button;
    TextView activity_name;
    String profileUrl, phone;

    FloatingActionButton newDiaryNote;
    RecyclerView diaryRecyclerview;
    DiaryAdapter diaryAdapter;
    ArrayList<DiaryModel> diaryModelArrayList;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //    appBar
        back_button = findViewById(R.id.back_button);
        activity_name = findViewById(R.id.activity_name);
        profile_icon = findViewById(R.id.profile_icon);
        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");

        newDiaryNote = findViewById(R.id.new_diary_note_btn);
        diaryRecyclerview = findViewById(R.id.diary_recyclerview);
        diaryRecyclerview.setHasFixedSize(true);
        diaryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        diaryModelArrayList = new ArrayList<>();
        diaryAdapter = new DiaryAdapter(diaryModelArrayList, this);

        diaryRecyclerview.setAdapter(diaryAdapter);

        firestore = FirebaseFirestore.getInstance();

        newDiaryNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Diary.this, NewDiaryNote.class));
            }
        });

        showData();

    }

    private void showData() {
        firestore.collection(phone).document("diary").collection("diary").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            DiaryModel obj = d.toObject(DiaryModel.class);
                            diaryModelArrayList.add(obj);
                        }
                        diaryAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        appbarFunctionality();
    }

    protected void appbarFunctionality() {
        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(phone);

//        Back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Diary.super.onBackPressed();
            }
        });

//      Activity name set
        activity_name.setText("Diary");

//      Profile icon set
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Data d = snapshot.getValue(Data.class);
                    profileUrl = d.getProfileUrl();
                    Picasso.get().load(profileUrl).into(profile_icon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
}
