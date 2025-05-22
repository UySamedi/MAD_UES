package com.example.uesapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.uesapp.Myapi.ApiInterface;
import com.example.uesapp.Myapi.AuthTokenManager;
import com.example.uesapp.Myapi.ProfileResponse;
import com.example.uesapp.Myapi.RetrofitInstance;
import com.example.uesapp.R;
import com.example.uesapp.Register.LoginActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private TextView userName, userEmail;
    private ImageView profileImage, btnEditProfile, btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);

        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);
        profileImage = view.findViewById(R.id.profile_image);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnLogout = view.findViewById(R.id.btn_logout);

        setupListeners();
        fetchProfileData();

        return view;
    }

    private void setupListeners() {
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to EditProfileActivity (create this activity if needed)
            // startActivity(new Intent(requireContext(), EditProfileActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            AuthTokenManager.clearAuthToken(requireContext());
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        });
    }

    private void fetchProfileData() {
        Context context = requireContext();

        if (!AuthTokenManager.isLoggedIn(context)) {
            redirectToLogin();
            return;
        }

        ApiInterface apiInterface = RetrofitInstance.getInstance(context).getApiInterface();
        Call<ProfileResponse> call = apiInterface.getProfile();

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.d(TAG, "API Response code: " + response.code());
                Log.d(TAG, "Raw response: " + response.raw().toString());
                if (response.body() != null) {
                    Log.d(TAG, "Response body: " + new Gson().toJson(response.body()));
                } else {
                    Log.d(TAG, "Response body is null");
                }

                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profile = response.body();
                    updateUI(profile);
                } else {
                    handleError(response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e(TAG, "Network error: " + t.getMessage());
                showError("Network error");
            }
        });
    }

    private void updateUI(ProfileResponse profile) {
        requireActivity().runOnUiThread(() -> {
            userName.setText(profile.getName() != null ? profile.getName() : "Unknown");
            userEmail.setText(profile.getEmail() != null ? profile.getEmail() : "No email");

            if (profile.getProfileImageUrl() != null && !profile.getProfileImageUrl().isEmpty()) {
                Glide.with(requireContext())
                        .load(profile.getProfileImageUrl())
                        .placeholder(R.drawable.profile_image)
                        .error(R.drawable.profile_image)
                        .into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.profile_image);
            }
        });
    }

    private void handleError(int statusCode) {
        if (statusCode == 401) {
            AuthTokenManager.clearAuthToken(requireContext());
            showError("Session expired, please log in again");
            redirectToLogin();
        } else {
            showError("Error loading profile (Code: " + statusCode + ")");
        }
    }

    private void redirectToLogin() {
        startActivity(new Intent(requireContext(), LoginActivity.class));
        requireActivity().finish();
    }

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}