package com.example.uesapp.Departments;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.uesapp.FormRegisterStudy.SiActivity;
import com.example.uesapp.R;
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

        // Find and set click listeners for back buttons and register button (unchanged)
        ImageView btnBack = findViewById(R.id.btn_back_ite);
        TextView textBack = findViewById(R.id.textView11);
        Button btnRegister = findViewById(R.id.btn_register_std);

        btnBack.setOnClickListener(v -> finish());
        textBack.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ITEActivity.this, SiActivity.class);
            startActivity(intent);
        });
    }
}