package com.example.uesapp.Departments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uesapp.FormRegisterStudy.SiActivity;
import com.example.uesapp.R;

public class SCAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scaactivity);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the back button ImageView
        ImageView btnBack = findViewById(R.id.btn_back_sca);
        // Find the back TextView
        TextView textBack = findViewById(R.id.textView11);

        Button btnRegister = findViewById(R.id.btn_register_std);

        // Set click listener for back button ImageView
        btnBack.setOnClickListener(v -> {
            finish(); // Closes the current activity and returns to the previous screen
        });

        // Set click listener for back TextView
        textBack.setOnClickListener(v -> {
            finish(); // Closes the current activity and returns to the previous screen
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(SCAActivity.this, SiActivity.class);
            startActivity(intent);
        });
    }
}