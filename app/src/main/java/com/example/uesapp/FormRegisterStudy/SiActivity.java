package com.example.uesapp.FormRegisterStudy;

import android.app.DatePickerDialog;
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

import com.example.uesapp.MainActivity;
import com.example.uesapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SiActivity extends AppCompatActivity {

    private EditText editFirstName, editLastName, editDob, editPob, editEducation;
    private RadioGroup genderGroup;
    private RadioButton femaleButton, maleButton;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_si);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupDatePicker();
        setupButtonListeners();
    }

    private void initializeViews() {
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editDob = findViewById(R.id.edit_dob);
        editPob = findViewById(R.id.edit_pob);
        editEducation = findViewById(R.id.edit_education);
        genderGroup = findViewById(R.id.gender_group_si);
        femaleButton = findViewById(R.id.female_button);
        maleButton = findViewById(R.id.male_button);
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            editDob.setText(dateFormatter.format(calendar.getTime()));
        };

        editDob.setOnClickListener(v -> new DatePickerDialog(
                SiActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private void setupButtonListeners() {
        Button btnNextSi = findViewById(R.id.btn_next_si);
        btnNextSi.setOnClickListener(v -> validateAndProceed());

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> navigateToMainActivity());
    }

    private void validateAndProceed() {
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String dob = editDob.getText().toString().trim();
        String pob = editPob.getText().toString().trim();
        String education = editEducation.getText().toString().trim();
        String gender = femaleButton.isChecked() ? "female" : "male";

        if (firstName.isEmpty()) {
            editFirstName.setError("First name is required");
            return;
        }
        if (lastName.isEmpty()) {
            editLastName.setError("Last name is required");
            return;
        }
        if (dob.isEmpty()) {
            editDob.setError("Date of birth is required");
            return;
        }
        if (pob.isEmpty()) {
            editPob.setError("Place of birth is required");
            return;
        }
        if (education.isEmpty()) {
            editEducation.setError("Education is required");
            return;
        }

        Intent intent = new Intent(SiActivity.this, PgiActivity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("gender", gender);
        intent.putExtra("dob", dob);
        intent.putExtra("address", pob);
        startActivity(intent);
    }

    private void navigateToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}