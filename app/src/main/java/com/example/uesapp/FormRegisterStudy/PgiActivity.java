package com.example.uesapp.FormRegisterStudy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uesapp.R;

public class PgiActivity extends AppCompatActivity {

    private EditText editFatherName, editFatherJob, editMotherName, editMotherJob, editPhoneNumber;
    private RadioGroup radioGroupFatherAlive, radioGroupMotherAlive;
    private RadioButton radioYesFather, radioNoFather, radioYesMother, radioNoMother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pgi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupButtonListeners();
    }

    private void initializeViews() {
        editFatherName = findViewById(R.id.edit_father_name);
        editFatherJob = findViewById(R.id.edit_father_job);
        editMotherName = findViewById(R.id.edit_mother_name);
        editMotherJob = findViewById(R.id.edit_mother_job);
        editPhoneNumber = findViewById(R.id.edit_phone_number);

        radioGroupFatherAlive = findViewById(R.id.radio_group_father_alive);
        radioGroupMotherAlive = findViewById(R.id.radio_group_mother_alive);
        radioYesFather = findViewById(R.id.radio_yes_father);
        radioNoFather = findViewById(R.id.radio_no_father);
        radioYesMother = findViewById(R.id.radio_yes_mother);
        radioNoMother = findViewById(R.id.radio_no_mother);
    }

    private void setupButtonListeners() {
        Button btnNextPgi = findViewById(R.id.btn_next_pgi);
        btnNextPgi.setOnClickListener(v -> validateAndProceed());

        ImageView btnBackPgi = findViewById(R.id.btn_back_pgi);
        btnBackPgi.setOnClickListener(v -> {
            startActivity(new Intent(PgiActivity.this, SiActivity.class));
            finish();
        });
    }

    private void validateAndProceed() {
        String fatherName = editFatherName.getText().toString().trim();
        String fatherJob = editFatherJob.getText().toString().trim();
        String motherName = editMotherName.getText().toString().trim();
        String motherJob = editMotherJob.getText().toString().trim();
        String phoneNumber = editPhoneNumber.getText().toString().trim();

        if (fatherName.isEmpty()) {
            editFatherName.setError("Father's name is required");
            return;
        }
        if (fatherJob.isEmpty()) {
            editFatherJob.setError("Father's job is required");
            return;
        }
        if (motherName.isEmpty()) {
            editMotherName.setError("Mother's name is required");
            return;
        }
        if (motherJob.isEmpty()) {
            editMotherJob.setError("Mother's job is required");
            return;
        }
        if (phoneNumber.isEmpty()) {
            editPhoneNumber.setError("Phone number is required");
            return;
        }
        if (!radioYesFather.isChecked() && !radioNoFather.isChecked()) {
            Toast.makeText(this, "Please select father's status", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!radioYesMother.isChecked() && !radioNoMother.isChecked()) {
            Toast.makeText(this, "Please select mother's status", Toast.LENGTH_SHORT).show();
            return;
        }

        int fatherAlive = radioYesFather.isChecked() ? 1 : 0;
        int motherAlive = radioYesMother.isChecked() ? 1 : 0;

        Intent intent = getIntent();
        Intent nextIntent = new Intent(PgiActivity.this, SchoolinformationActivity.class);

        // Pass through previous data
        nextIntent.putExtras(intent);

        // Add new data
        nextIntent.putExtra("fatherName", fatherName);
        nextIntent.putExtra("fatherJob", fatherJob);
        nextIntent.putExtra("fatherAlive", fatherAlive);
        nextIntent.putExtra("motherName", motherName);
        nextIntent.putExtra("motherJob", motherJob);
        nextIntent.putExtra("motherAlive", motherAlive);
        nextIntent.putExtra("phone", phoneNumber);
        nextIntent.putExtra("phonenumber", phoneNumber);

        startActivity(nextIntent);
    }
}