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

public class TeeY2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tee_y2);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the next and back buttons
        Button btnNext = findViewById(R.id.btn_next_tee_y2);
        Button btnBack = findViewById(R.id.btn_back_tee_y2);

        // Set click listener for next button
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(TeeY2Activity.this, TeeY3Activity.class);
            startActivity(intent);
        });

        // Set click listener for back button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TeeY2Activity.this, TeeY1Activity.class);
            startActivity(intent);
        });
    }
}