package com.example.uesapp.FormRegisterStudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import com.example.uesapp.MainActivity;
import com.example.uesapp.Myapi.ApiInterface;
import com.example.uesapp.Myapi.AuthTokenManager;
import com.example.uesapp.Myapi.Department;
import com.example.uesapp.Myapi.DepartmentResponse;
import com.example.uesapp.Myapi.RegistrationResponse;
import com.example.uesapp.Myapi.RetrofitInstance;
import com.example.uesapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    private Spinner spinnerMajor;
    private ActivityResultLauncher<Intent> photoPickerLauncher;
    private ActivityResultLauncher<Intent> diplomaPickerLauncher;
    private List<Department> departments;
    private ArrayAdapter<String> spinnerAdapter;
    private Uri photoUri, diplomaUri;
    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        checkStoragePermission(); // Check for storage permission at startup
        initializeViews();
        setupImagePickers();
        fetchDepartments(); // Attempt to fetch from API, fallback to resources if failed
        setupButtonListeners();
    }

    private void checkStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ uses granular permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, STORAGE_PERMISSION_CODE);
            }
        } else {
            // Android 12 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Storage Permission Denied. Cannot upload images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeViews() {
        spinnerMajor = findViewById(R.id.spinner_major);
        departments = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(spinnerAdapter);
    }

    private void setupImagePickers() {
        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleImageResult(result, true));

        diplomaPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleImageResult(result, false));
    }

    private void handleImageResult(androidx.activity.result.ActivityResult result, boolean isPhoto) {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri uri = result.getData().getData();
            if (uri != null) {
                if (isPhoto) {
                    photoUri = uri;
                    Toast.makeText(this, "Photo selected", Toast.LENGTH_SHORT).show();
                } else {
                    diplomaUri = uri;
                    Toast.makeText(this, "Diploma selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setupButtonListeners() {
        Button btnUploadPhoto = findViewById(R.id.btn_upload_photo);
        btnUploadPhoto.setOnClickListener(v -> launchImagePicker(true));

        Button btnUploadDiploma = findViewById(R.id.btn_upload_diploma);
        btnUploadDiploma.setOnClickListener(v -> launchImagePicker(false));

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> validateAndSubmit());

        ImageView btnBackUpload = findViewById(R.id.btn_back_upload);
        btnBackUpload.setOnClickListener(v -> {
            startActivity(new Intent(UploadActivity.this, SchoolinformationActivity.class));
            finish();
        });
    }

    private void launchImagePicker(boolean isPhoto) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (isPhoto) {
            photoPickerLauncher.launch(intent);
        } else {
            diplomaPickerLauncher.launch(intent);
        }
    }

    private void validateAndSubmit() {
        if (!validateInputs()) return;
        if (!validateImages()) return;
        if (!AuthTokenManager.isLoggedIn(this)) {
            Toast.makeText(this, "Please log in to proceed", Toast.LENGTH_SHORT).show();
            return;
        }

        Department selectedDepartment = departments.get(spinnerMajor.getSelectedItemPosition());
        if (selectedDepartment == null || selectedDepartment.getId() == -1) {
            Toast.makeText(this, "Please select a valid department", Toast.LENGTH_SHORT).show();
            return;
        }

        submitRegistration(selectedDepartment.getId());
    }

    private boolean validateInputs() {
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "Missing required data", Toast.LENGTH_SHORT).show();
            return false;
        }

        String[] requiredFields = {
                "firstName", "lastName", "gender", "dob", "address",
                "fatherName", "motherName", "phone", "phonenumber",
                "educationName", "educationDate", "educationGrade"
        };

        for (String field : requiredFields) {
            if (intent.getStringExtra(field) == null || intent.getStringExtra(field).isEmpty()) {
                Toast.makeText(this, "Missing required field: " + field, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private boolean validateImages() {
        if (photoUri == null) {
            Toast.makeText(this, "Please upload a photo", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (diplomaUri == null) {
            Toast.makeText(this, "Please upload a diploma", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void submitRegistration(int departmentId) {
        try {
            Intent intent = getIntent();
            MultipartBody.Part picturePart = createImagePart("picture", photoUri);
            if (picturePart == null) {
                Toast.makeText(this, "Failed to process photo. Please try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            MultipartBody.Part diplomaPart = createImagePart("education_image", diplomaUri);
            if (diplomaPart == null) {
                Toast.makeText(this, "Failed to process diploma. Please try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiInterface apiService = RetrofitInstance.getInstance(this).getApiInterface();
            Call<RegistrationResponse> call = apiService.createRegistration(
                    createRequestBody("1"), // user_id (hardcoded for example)
                    createRequestBody(String.valueOf(departmentId)),
                    createRequestBody(intent.getStringExtra("firstName")),
                    createRequestBody(intent.getStringExtra("lastName")),
                    createRequestBody(intent.getStringExtra("gender")),
                    createRequestBody(intent.getStringExtra("dob")),
                    createRequestBody(intent.getStringExtra("address")),
                    createRequestBody(intent.getStringExtra("phone")),
                    createRequestBody(intent.getStringExtra("phonenumber")),
                    createRequestBody(intent.getStringExtra("fatherName")),
                    createRequestBody(intent.getStringExtra("fatherJob")),
                    createRequestBody(String.valueOf(intent.getIntExtra("fatherAlive", 0))),
                    createRequestBody(intent.getStringExtra("motherName")),
                    createRequestBody(intent.getStringExtra("motherJob")),
                    createRequestBody(String.valueOf(intent.getIntExtra("motherAlive", 0))),
                    createRequestBody(intent.getStringExtra("educationName")),
                    createRequestBody(intent.getStringExtra("educationDate")),
                    createRequestBody("Phnom Penh"), // Hardcoded location
                    createRequestBody(intent.getStringExtra("educationGrade")),
                    picturePart,
                    diplomaPart
            );

            call.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        handleSuccessResponse(response.body());
                    } else {
                        handleErrorResponse(response);
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    Toast.makeText(UploadActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Failure: " + t.getMessage(), t);
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("UploadActivity", "Submission error", e);
        }
    }

    private MultipartBody.Part createImagePart(String partName, Uri uri) {
        try {
            byte[] bytes = getBytesFromUri(uri);
            if (bytes == null) {
                Log.e("UploadActivity", "Failed to read bytes from URI: " + uri.toString());
                return null;
            }

            RequestBody requestFile = RequestBody.create(
                    MediaType.parse("image/*"),
                    bytes
            );
            return MultipartBody.Part.createFormData(
                    partName,
                    "upload.jpg",
                    requestFile
            );
        } catch (Exception e) {
            Log.e("UploadActivity", "Error creating image part for " + partName + ": " + e.getMessage(), e);
            return null;
        }
    }

    private RequestBody createRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                Log.e("UploadActivity", "InputStream is null for URI: " + uri.toString());
                return null;
            }

            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (IOException e) {
            Log.e("UploadActivity", "IOException while reading URI: " + uri.toString() + ", Error: " + e.getMessage(), e);
            throw e; // Re-throw to ensure the caller handles it
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("UploadActivity", "Error closing InputStream: " + e.getMessage(), e);
                }
            }
        }
    }

    private void handleSuccessResponse(RegistrationResponse response) {
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void handleErrorResponse(Response<RegistrationResponse> response) {
        try {
            String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Toast.makeText(this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
            Log.e("API Response", "Error: " + response.code() + ", " + error);

            if (response.code() == 401) {
                RetrofitInstance.getInstance(this).refreshToken(this);
            }
        } catch (IOException e) {
            Log.e("UploadActivity", "Error reading error response: " + e.getMessage(), e);
            Toast.makeText(this, "Error reading response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDepartments() {
        ApiInterface apiService = RetrofitInstance.getInstance(this).getApiInterface();
        apiService.getDepartments().enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateDepartmentList(response.body().getDepartments());
                } else {
                    handleDepartmentFetchError();
                    try {
                        Log.e("API Error", "Unsuccessful response: " + (response.errorBody() != null ? response.errorBody().string() : "No error body"));
                    } catch (IOException e) {
                        Log.e("UploadActivity", "Error reading API error response: " + e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                handleDepartmentFetchError();
                Log.e("UploadActivity", "Department fetch failed: " + t.getMessage(), t);
            }
        });
    }

    private void updateDepartmentList(List<Department> newDepartments) {
        departments.clear();
        List<String> departmentNames = new ArrayList<>();
        if (newDepartments == null || newDepartments.isEmpty()) {
            departments.add(new Department(-1, "Select Major"));
            departmentNames.add("Select Major");
        } else {
            for (Department dept : newDepartments) {
                departments.add(dept);
                departmentNames.add(dept.getName());
            }
        }
        spinnerAdapter.clear();
        spinnerAdapter.addAll(departmentNames);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void handleDepartmentFetchError() {
        departments.clear();
        List<String> departmentNames = new ArrayList<>();
        String[] majors = getResources().getStringArray(R.array.major_options);
        for (int i = 0; i < majors.length; i++) {
            departments.add(new Department(i, majors[i]));
            departmentNames.add(majors[i]);
        }
        spinnerAdapter.clear();
        spinnerAdapter.addAll(departmentNames);
        spinnerAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Failed to load departments, using default options", Toast.LENGTH_SHORT).show();
    }
}