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

import com.example.uesapp.R;

public class SchoolinformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schoolinformation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle btn_next_school_infor
        Button btnNextSchoolInfor = findViewById(R.id.btn_next_school_infor);
        btnNextSchoolInfor.setOnClickListener(v -> {
            Intent intent = new Intent(SchoolinformationActivity.this, UploadActivity.class);
            startActivity(intent);
        });

        // Handle btn_back_pgi
        ImageView btnBackPgi = findViewById(R.id.btn_back_sch);
        btnBackPgi.setOnClickListener(v -> {
            Intent intent = new Intent(SchoolinformationActivity.this, PgiActivity.class);
            startActivity(intent);
            finish(); // Optional: finish current activity to prevent stacking
        });
    }
}