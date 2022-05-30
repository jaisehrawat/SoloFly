package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    ChipNavigationBar bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnView = findViewById(R.id.bottomNav);

//        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//
//                if(id == R.id.dashboard)
//                {
//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//
//                }
//                else if(id == R.id.home)
//                {
//
//                }
//                else if (id == R.id.upcomings)
//                {
//
//                }
//                else
//                {
//
//                }
//
//                return false;
//            }
//        });

        loadFragment(new dashboard());
        bnView.setOnItemSelectedListener(this);
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
}