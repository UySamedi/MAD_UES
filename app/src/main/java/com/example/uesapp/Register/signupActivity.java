package com.example.uesapp.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uesapp.Myapi.ApiInterface;
import com.example.uesapp.Myapi.ApiResponse;
import com.example.uesapp.Myapi.RegisterRequest;
import com.example.uesapp.Myapi.RetrofitInstance;
import com.example.uesapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signupActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPassword, editConfirmPassword;
    private Button btnSignup;
    private TextView loginText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();

        btnSignup.setOnClickListener(v -> validateAndRegister());
        loginText.setOnClickListener(v -> navigateToLogin());
    }

    private void initViews() {
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        editConfirmPassword = findViewById(R.id.edit_confirm_password);  // fixed ID here
        btnSignup = findViewById(R.id.btn_signup);
        loginText = findViewById(R.id.loginText);
    }

    private void validateAndRegister() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "សូមបញ្ចូលព័ត៌មានគ្រប់បែបយ៉ាង", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(this, "អ៊ីមែលមិនត្រឹមត្រូវ", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(password)) {
            Toast.makeText(this, "លេខសម្ងាត់ត្រូវមានយ៉ាងហោចណាស់ 8 តួអក្សរ", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "លេខសម្ងាត់មិនផ្គូផ្គង", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(name, email, password, confirmPassword);
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void registerUser(String name, String email, String password, String confirmPassword) {
        showProgressDialog();

        ApiInterface apiInterface = RetrofitInstance.getInstance(this).getApiInterface();
        RegisterRequest registerRequest = new RegisterRequest(name, email, password, confirmPassword);

        Call<ApiResponse> call = apiInterface.register(registerRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(signupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    navigateToLogin();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(signupActivity.this, "បរាជ័យ៖ " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(signupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleErrorResponse(Response<ApiResponse> response) {
        if (response.code() == 400) {
            Toast.makeText(this, "សំណើមិនត្រឹមត្រូវ", Toast.LENGTH_SHORT).show();
        } else if (response.code() == 500) {
            Toast.makeText(this, "បញ្ហា Server, សូមព្យាយាមម្តងទៀត", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ការចុះឈ្មោះបរាជ័យ", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("កំពុងចុះឈ្មោះ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null) {
                hideKeyboard();
                view.clearFocus();
            }
        }
        return super.onTouchEvent(event);
    }
}
