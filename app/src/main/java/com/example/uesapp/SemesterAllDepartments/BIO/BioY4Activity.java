package com.example.uesapp.SemesterAllDepartments.BIO;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uesapp.Departments.BIOActivity;
import com.example.uesapp.R;

public class BioY4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bio_y4);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the next and back buttons
        Button btnNext = findViewById(R.id.btn_next_bio_y4);
        Button btnBack = findViewById(R.id.btn_back_bio_y4);

        // Set click listener for next button (optional, no forward navigation)
        btnNext.setOnClickListener(v -> {
            // No forward navigation; optionally navigate to BIOActivity or disable
            Intent intent = new Intent(BioY4Activity.this, BIOActivity.class);
            startActivity(intent);
        });

        // Set click listener for back button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(BioY4Activity.this, BioY3Activity.class);
            startActivity(intent);
        });
    }
}