package com.example.solofly;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.solofly.Adapter.ToDoAdapter;
import com.example.solofly.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDo extends AppCompatActivity implements OnDialogCloseListner {

    RecyclerView recyclerView;
    FloatingActionButton mFab;
    FirebaseFirestore firestore;
    ToDoAdapter adapter;
    List<ToDoModel> mList;
    Query query;
    ListenerRegistration listenerRegistration;

    //    Profile
    ImageView profile_icon, back_button;
    TextView activity_name;
    public static String profileUrl, phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone", "");

        back_button = findViewById(R.id.back_button);
        activity_name = findViewById(R.id.activity_name);
        profile_icon = findViewById(R.id.profile_icon);

        recyclerView = findViewById(R.id.recyclerview_todo);
        mFab = findViewById(R.id.new_task);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ToDo.this));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTask.newInstance().show(getSupportFragmentManager(), NewTask.TAG);
            }
        });

        mList = new ArrayList<>();
        adapter = new ToDoAdapter(ToDo.this, mList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        showData();
        recyclerView.setAdapter(adapter);
    }

    private void showData() {
        query = firestore.collection(phone).document("todo").collection("todo");

        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        String id = documentChange.getDocument().getId();
                        ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);
                        mList.add(toDoModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                listenerRegistration.remove();
            }
        });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList.clear();
        showData();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        appbarFunctionality();
    }

    protected void appbarFunctionality() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(phone);

//        Back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDo.super.onBackPressed();
            }
        });

//      Activity name set
        activity_name.setText("ToDo");

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
