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
import com.example.uesapp.SemesterAllDepartments.IteEGActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ITEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iteactivity);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the TextView
        TextView textIntroduction = findViewById(R.id.text_ite_introduction);

        // Example API response (replace with actual API call)
        String jsonResponse = "{\n" +
                "  \"message\": \"Departments Retrieved successfully!\",\n" +
                "  \"department\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Information Technology Engineering\",\n" +
                "    \"description\": \"The Bachelor of Information Technology Engineering program at the Faculty of Engineering, Royal University of Phnom Penh provides comprehensive, hands-on training in IT, combining theoretical and practical learning. It prepares students for tech industries through coursework in computer software, networks, and advanced topics like AI and mobile development, along with mandatory practicums and internships to build real-world experience and skills.\"\n" +
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
        ImageView btnBack = findViewById(R.id.btn_back_ite);
        TextView textBack = findViewById(R.id.textView11);
        Button btnRegister = findViewById(R.id.btn_register_std);
        Button btnViewMore = findViewById(R.id.btn_view_more_ite);

        // Set click listener for back button ImageView
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ITEActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close ITEActivity to prevent stacking
        });

        // Set click listener for back TextView
        textBack.setOnClickListener(v -> {
            Intent intent = new Intent(ITEActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close ITEActivity to prevent stacking
        });

        // Set click listener for register button
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ITEActivity.this, SiActivity.class);
            startActivity(intent);
        });

        // Set click listener for view more button
        btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(ITEActivity.this, IteEGActivity.class);
            startActivity(intent);
        });
    }
}