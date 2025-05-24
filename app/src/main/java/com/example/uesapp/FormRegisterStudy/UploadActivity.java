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

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle btn_submit
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: finish current activity to prevent stacking
        });

        // Handle btn_back_pgi
        ImageView btnBackPgi = findViewById(R.id.btn_back_upload);
        btnBackPgi.setOnClickListener(v -> {
            Intent intent = new Intent(UploadActivity.this, SchoolinformationActivity.class);
            startActivity(intent);
            finish(); // Optional: finish current activity to prevent stacking
        });
    }
}