package com.example.uesapp.Register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uesapp.MainActivity;
import com.example.uesapp.Myapi.ApiInterface;
import com.example.uesapp.Myapi.ApiResponse;
import com.example.uesapp.Myapi.LoginRequest;
import com.example.uesapp.Myapi.RetrofitInstance;
import com.example.uesapp.Myapi.AuthTokenManager;
import com.example.uesapp.R;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private TextView textRegister, textForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (AuthTokenManager.isLoggedIn(this)) {
            Log.d(TAG, "User already logged in, navigating to MainActivity");
            navigateToMainActivity();
            return;
        }

        initializeViews();
        setupPasswordToggle();
        setupLoginButton();
        setupRegisterLink();
        setupForgotPasswordLink();
    }

    private void setupPasswordToggle() {
        // Implement password toggle functionality if needed
    }

    private void initializeViews() {
        editEmail = findViewById(R.id.edit_email_login);
        editPassword = findViewById(R.id.edit_password_login);
        btnLogin = findViewById(R.id.btn_login);
        textRegister = findViewById(R.id.signupText);
        textForgotPassword = findViewById(R.id.forgot_password_text); // Fixed ID to match XML
    }

    private void setupLoginButton() {
        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (validateInputs(email, password)) {
                loginUser(email, password);
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter a valid email");
            editEmail.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            editPassword.setError("Password must be at least 6 characters");
            editPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void setupRegisterLink() {
        textRegister.setOnClickListener(v -> {
            Log.d(TAG, "Navigating to RegisterActivity");
            startActivity(new Intent(LoginActivity.this, signupActivity.class));
        });
    }

    private void setupForgotPasswordLink() {
        if (textForgotPassword != null) {
            textForgotPassword.setOnClickListener(v -> {
                Toast.makeText(this, "Navigate to Forgot Password", Toast.LENGTH_SHORT).show();
                // Add your forgot password navigation logic here
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyboard();
            View view = getCurrentFocus();
            if (view != null) view.clearFocus();
        }
        return super.onTouchEvent(event);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void loginUser(String email, String password) {
        showLoading(true);
        Log.d(TAG, "Attempting login with email: " + email);

        ApiInterface apiInterface = RetrofitInstance.getInstance(this).getApiInterface();
        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<ApiResponse> call = apiInterface.login(loginRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                showLoading(false);
                Log.d(TAG, "API Response code: " + response.code());

                if (response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    Log.d(TAG, "Full response body: " + new Gson().toJson(apiResponse));
                    Log.d(TAG, "Message: " + apiResponse.getMessage());
                    Log.d(TAG, "Root access_token: " + apiResponse.getAccess_token());
                    Log.d(TAG, "Data: " + (apiResponse.getData() != null ? new Gson().toJson(apiResponse.getData()) : "null"));
                } else {
                    Log.d(TAG, "Response body is null");
                }

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String token = apiResponse.getAccess_token();

                    if (token == null && "Login successful".equals(apiResponse.getMessage())) {
                        token = "mock_token_123";
                        Log.w(TAG, "No token in response; using mock token: " + token);
                    }

                    if (token != null && !token.isEmpty()) {
                        Log.d(TAG, "Token received: " + token);
                        AuthTokenManager.saveAuthToken(LoginActivity.this, token);
                        String savedToken = AuthTokenManager.getAuthToken(LoginActivity.this);
                        Log.d(TAG, "Saved token: " + savedToken);
                        if (token.equals(savedToken)) {
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Token storage failed", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e(TAG, "No valid token found in response");
                        String errorMessage = apiResponse.getMessage() != null ? apiResponse.getMessage() : "Login failed: No token received";
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                } else {
                    String errorMsg = "Login failed";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                            Log.e(TAG, "Error body: " + errorMsg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing error body", e);
                    }
                    handleLoginFailure(response.code(), errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Network failure", t);
            }
        });
    }

    private void handleLoginFailure(int statusCode, String errorBody) {
        String errorMessage;
        switch (statusCode) {
            case 401:
                errorMessage = "Invalid email or password";
                break;
            case 403:
                errorMessage = "Account not verified";
                break;
            case 404:
                errorMessage = "User not found";
                break;
            case 500:
                errorMessage = "Server error, please try again later";
                break;
            default:
                errorMessage = "Login failed: " + errorBody + " (Error " + statusCode + ")";
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void navigateToMainActivity() {
        Log.d(TAG, "Navigating to MainActivity");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showLoading(boolean show) {
        btnLogin.setEnabled(!show);
        btnLogin.setText(show ? "Logging in..." : "Login");
    }
}