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
import com.example.uesapp.SemesterAllDepartments.TeeEgActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class TEEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teeactivity);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the TextView
        TextView textIntroduction = findViewById(R.id.text_tee_introduction);

        // Example API response (replace with actual API call)
        String jsonResponse = "{\n" +
                "  \"message\": \"Departments Retrieved successfully!\",\n" +
                "  \"department\": {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Telecommunication and Electronic Engineering\",\n" +
                "    \"description\": \"The Telecommunication and Electronics Engineering program combines telecommunication and electronics technologies to educate students in designing communication systems and electronic devices. It prepares graduates with technical, research, leadership, and entrepreneurial skills for careers in various sectors such as telecom companies, media stations, transportation hubs, factories, and public or private institutions.\"\n" +
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
        ImageView btnBack = findViewById(R.id.btn_back_tee);
        TextView textBack = findViewById(R.id.textView11);
        Button btnRegister = findViewById(R.id.btn_register_std);
        Button btnViewMore = findViewById(R.id.btn_view_more_tee);

        // Set click listener for back button ImageView
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TEEActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close TEEActivity to prevent stacking
        });

        // Set click listener for back TextView
        textBack.setOnClickListener(v -> {
            Intent intent = new Intent(TEEActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close TEEActivity to prevent stacking
        });

        // Set click listener for register button
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(TEEActivity.this, SiActivity.class);
            startActivity(intent);
        });

        // Set click listener for view more button
        btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(TEEActivity.this, TeeEgActivity.class);
            startActivity(intent);
        });
    }
}