package com.example.uesapp.SemesterAllDepartments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uesapp.Departments.ITEActivity;
import com.example.uesapp.R;
import com.example.uesapp.SemesterAllDepartments.ITE.IteY1Activity;

public class IteEGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ite_egactivity);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the next and back buttons
        Button btnNext = findViewById(R.id.btn_next_ite_eg);
        Button btnBack = findViewById(R.id.btn_back_ite_eg);

        // Set click listener for next button
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(IteEGActivity.this, IteY1Activity.class);
            startActivity(intent);
        });

        // Set click listener for back button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(IteEGActivity.this, ITEActivity.class);
            startActivity(intent);
        });
    }
}