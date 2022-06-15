package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    ChipNavigationBar bnView;
    Toolbar toolbar;
    ImageView bellIcon, profile_icon_dashboard;
    TextView name_dashboard;
    String name, phone, profileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnView = findViewById(R.id.bottomNav);
        toolbar = findViewById(R.id.toolbar);
        profile_icon_dashboard = findViewById(R.id.profile_icon_dashboard);
        bellIcon = findViewById(R.id.bellIcon);
        name_dashboard = findViewById(R.id.name_dashboard);

        loadFragment(new dashboard());
        bnView.setOnItemSelectedListener(this);

        bellIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotificationPage.class);
                startActivity(intent);
            }
        });

        profile_icon_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            Toast.makeText(this, "Fragment Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_menu, menu);
        return true;
    }

    @Override
    public void onItemSelected(int i) {

        Fragment fragment = null;

        switch (i) {
            case R.id.dashboard:
                fragment = new dashboard();
                break;

            case R.id.upcomings:
                fragment = new upCommings();
                break;

            case R.id.home:
                fragment = new home();
                break;
        }

        loadFragment(fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
        phone = preferences.getString("phone","");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(phone);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Data d = snapshot.getValue(Data.class);
                    name = d.getName();
                    profileUrl = d.getProfileUrl();
                    name_dashboard.setText(name);
                    Picasso.get().load(profileUrl).into(profile_icon_dashboard);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}