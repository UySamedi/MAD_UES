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

public class DEEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deeactivity);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Fix: Declare as TextView instead of View
        TextView textIntroduction = findViewById(R.id.text_ee_introduction);

        // Example API response (replace with actual API call)
        String jsonResponse = "{\n" +
                "  \"message\": \"Departments Retrieved successfully!\",\n" +
                "  \"department\": {\n" +
                "    \"id\": 7,\n" +
                "    \"name\": \"Environmental Engineering\",\n" +
                "    \"description\": \" The Department of Environmental Engineering offers a high-quality bachelor's program, supported by KOICA and GIST, to equip motivated students—especially in developing countries like Cambodia—with the knowledge, skills, and technologies needed to effectively assess and manage environmental problems caused by human activities, and to strengthen local capacity for sustainable environmental conservation.\"\n" +
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
        } // Fix: Added closing brace for try-catch block

        // Find the back button ImageView
        ImageView btnBack = findViewById(R.id.btn_back_ee);
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
            Intent intent = new Intent(DEEActivity.this, SiActivity.class);
            startActivity(intent);
        });
    } // Fix: Ensure closing brace for onCreate method
}