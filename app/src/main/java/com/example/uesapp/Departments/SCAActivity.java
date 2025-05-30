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
import com.example.uesapp.MainActivity;
import com.example.uesapp.R;
import com.example.uesapp.SemesterAllDepartments.ScaEGActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

        // Find the TextView
        TextView textIntroduction = findViewById(R.id.text_sca_introduction);

        // Example API response (replace with actual API call)
        String jsonResponse = "{\n" +
                "  \"message\": \"Departments Retrieved successfully!\",\n" +
                "  \"department\": {\n" +
                "    \"id\": 6,\n" +
                "    \"name\": \"Automation & Supply Chain Systems Engineering\",\n" +
                "    \"description\": \" The Automation and Supply Chain Systems Engineering (ASCSE) program, established in partnership with SIIT, Thammasat University, aims to prepare students for Industry 4.0 by providing education in automation, digital manufacturing, and supply chain optimization. Through a flexible 2+2 international bachelor's degree and industry collaboration, the program develops students’ technical and problem-solving skills to meet local and global workforce demands.\"\n" +
                "  }\n" +
                "}";

        try {
            // Parse JSON response
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject department = jsonObject.getJSONObject("department");
            String description = department.getString("description");

            // Set the description to the TextView
            textIntroduction.setText(description);
        } catch (JSONException e) {
            e.printStackTrace();
            textIntroduction.setText("Failed to load description.");
        }

        // Find buttons and TextView
        ImageView btnBack = findViewById(R.id.btn_back_sca);
        TextView textBack = findViewById(R.id.textView11);
        Button btnRegister = findViewById(R.id.btn_register_std);
        Button btnViewMore = findViewById(R.id.btn_view_more_sca);

        // Set click listener for back button ImageView
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SCAActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close SCAActivity to prevent stacking
        });

        // Set click listener for back TextView
        textBack.setOnClickListener(v -> {
            Intent intent = new Intent(SCAActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close SCAActivity to prevent stacking
        });

        // Set click listener for register button
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(SCAActivity.this, SiActivity.class);
            startActivity(intent);
        });

        // Set click listener for view more button
        btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(SCAActivity.this, ScaEGActivity.class);
            startActivity(intent);
        });
    }
}