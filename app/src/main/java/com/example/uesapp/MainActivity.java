package com.example.uesapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uesapp.Fragment.HomeFragment;
import com.example.uesapp.Fragment.ViewFragment;
import com.example.uesapp.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Show HomeFragment by default
        setCurrentFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.icon_home) {
                    setCurrentFragment(new HomeFragment());
                    return true;
                } else if (id == R.id.icon_view) {
                    setCurrentFragment(new ViewFragment());
                    return true;
                } else if (id == R.id.icon_profile) {
                    setCurrentFragment(new ProfileFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }
}
