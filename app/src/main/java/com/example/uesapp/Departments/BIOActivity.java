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

import org.json.JSONException;
import org.json.JSONObject;

public class BIOActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bioactivity);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the TextView
        TextView textIntroduction = findViewById(R.id.text_bio_introduction);

        // Example API response (replace with actual API call)
        String jsonResponse = "{\n" +
                "  \"message\": \"Departments Retrieved successfully!\",\n" +
                "  \"department\": {\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"Bio-Engineering\",\n" +
                "    \"description\": \"The Bio-Engineering/Biotechnology program is designed to equip students with foundational knowledge in biological and chemical processes across various fields such as medicine, food, agriculture, and the environment, while also fostering skills in communication, teamwork, leadership, and entrepreneurship for successful careers in industry and research\"\n" +
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

        // Find the back button ImageView
        ImageView btnBack = findViewById(R.id.btn_back_bio);
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
            Intent intent = new Intent(BIOActivity.this, SiActivity.class);
            startActivity(intent);
        });
    }
}