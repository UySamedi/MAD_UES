package com.example.uesapp.SemesterAllDepartments.TEE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uesapp.R;
import com.example.uesapp.SemesterAllDepartments.TeeEgActivity;

public class TeeY1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tee_y1);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the next and back buttons
        Button btnNext = findViewById(R.id.btn_next_tee_y1);
        Button btnBack = findViewById(R.id.btn_back_tee_y1);

        // Set click listener for next button
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(TeeY1Activity.this, TeeY2Activity.class);
            startActivity(intent);
        });

        // Set click listener for back button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TeeY1Activity.this, TeeEgActivity.class);
            startActivity(intent);
        });
    }
}