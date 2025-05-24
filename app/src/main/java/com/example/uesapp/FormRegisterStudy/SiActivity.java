package com.example.uesapp.FormRegisterStudy;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ImageView;

import com.example.uesapp.Fragment.HomeFragment;
import com.example.uesapp.MainActivity;
import com.example.uesapp.R;

public class SiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_si);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle btn_next_si
        Button btnNextSi = findViewById(R.id.btn_next_si);
        btnNextSi.setOnClickListener(v -> {
            Intent intent = new Intent(SiActivity.this, PgiActivity.class);
            startActivity(intent);
        });

        // Handle btn_back
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SiActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: finish current activity to prevent stacking
        });
    }
}