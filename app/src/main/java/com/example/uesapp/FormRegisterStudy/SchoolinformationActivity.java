package com.example.uesapp.FormRegisterStudy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uesapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SchoolinformationActivity extends AppCompatActivity {

    private EditText editPrimarySchool, editMiddleSchool, editHighSchool, editExamDate;
    private Spinner editGrade;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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

        initializeViews();
        setupGradeSpinner();
        setupDatePicker();
        setupButtonListeners();
    }

    private void initializeViews() {
        editPrimarySchool = findViewById(R.id.edit_primary_school);
        editMiddleSchool = findViewById(R.id.edit_middle_school);
        editHighSchool = findViewById(R.id.edit_high_school);
        editExamDate = findViewById(R.id.edit_exam_date);
        editGrade = findViewById(R.id.edit_grade);
    }

    private void setupGradeSpinner() {
        String[] grades = {"A", "B", "C", "D", "E"};
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, grades);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editGrade.setAdapter(gradeAdapter);
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            editExamDate.setText(dateFormatter.format(calendar.getTime()));
        };

        editExamDate.setOnClickListener(v -> new DatePickerDialog(
                SchoolinformationActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private void setupButtonListeners() {
        Button btnNextSchoolInfor = findViewById(R.id.btn_next_school_info);
        btnNextSchoolInfor.setOnClickListener(v -> validateAndProceed());

        ImageView btnBackPgi = findViewById(R.id.btn_back_sch);
        btnBackPgi.setOnClickListener(v -> {
            startActivity(new Intent(SchoolinformationActivity.this, PgiActivity.class));
            finish();
        });
    }

    private void validateAndProceed() {
        String primarySchool = editPrimarySchool.getText().toString().trim();
        String middleSchool = editMiddleSchool.getText().toString().trim();
        String highSchool = editHighSchool.getText().toString().trim();
        String examDate = editExamDate.getText().toString().trim();

        if (primarySchool.isEmpty()) {
            editPrimarySchool.setError("Primary school is required");
            return;
        }
        if (middleSchool.isEmpty()) {
            editMiddleSchool.setError("Middle school is required");
            return;
        }
        if (highSchool.isEmpty()) {
            editHighSchool.setError("High school is required");
            return;
        }
        if (examDate.isEmpty()) {
            editExamDate.setError("Exam date is required");
            return;
        }

        String grade = editGrade.getSelectedItem().toString();
        Intent intent = getIntent();
        Intent nextIntent = new Intent(SchoolinformationActivity.this, UploadActivity.class);

        // Pass through previous data
        nextIntent.putExtras(intent);

        // Add new data
        nextIntent.putExtra("educationName", highSchool);
        nextIntent.putExtra("educationDate", examDate);
        nextIntent.putExtra("educationLocation", "Phnom Penh");
        nextIntent.putExtra("educationGrade", grade);

        startActivity(nextIntent);
    }
}