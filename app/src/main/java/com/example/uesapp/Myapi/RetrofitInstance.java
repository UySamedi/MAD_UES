package com.example.uesapp.Myapi;

import android.content.Context;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RetrofitInstance {
    private static volatile RetrofitInstance instance;
    private final ApiInterface apiInterface;
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static final String TAG = "RetrofitInstance";

    private RetrofitInstance(Context context) {
        // Set up logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Build OkHttpClient with interceptors and timeouts
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String token = AuthTokenManager.getAuthToken(context);

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json");

                    if (token != null && !token.isEmpty()) {
                        String authToken = token.startsWith("Bearer ") ? token : "Bearer " + token;
                        requestBuilder.header("Authorization", authToken);
                        Log.d(TAG, "Adding auth token to header: " + authToken);
                    }

                    return chain.proceed(requestBuilder.build());
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        // Build Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static RetrofitInstance getInstance(Context context) {
        if (instance == null) {
            synchronized (RetrofitInstance.class) {
                if (instance == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("Context cannot be null");
                    }
                    instance = new RetrofitInstance(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public void refreshToken(Context context) {
        String refreshToken = AuthTokenManager.getRefreshToken(context);
        if (refreshToken == null || refreshToken.isEmpty()) {
            Log.e(TAG, "No refresh token available");
            AuthTokenManager.clearAuthToken(context);
            return;
        }

        // Create a new client without Authorization header for token refresh
        OkHttpClient refreshClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json");
                    return chain.proceed(requestBuilder.build());
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit refreshRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(refreshClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface refreshApi = refreshRetrofit.create(ApiInterface.class);
        Call<ApiResponse> call = refreshApi.refreshToken(new RefreshTokenRequest(refreshToken));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newToken = response.body().getMessage();
                    if (newToken != null && !newToken.isEmpty()) {
                        AuthTokenManager.saveAuthToken(context, newToken);
                        // Use SimpleDateFormat to get the current timestamp
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String currentTime = sdf.format(new Date());
                        Log.d(TAG, "Token refreshed successfully at " + currentTime);
                    } else {
                        Log.e(TAG, "No new token in response");
                        handleTokenRefreshFailure(context);
                    }
                } else {
                    Log.e(TAG, "Failed to refresh token: " + response.code() + " - " + response.message());
                    handleTokenRefreshFailure(context);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "Token refresh failed: " + t.getMessage(), t);
                handleTokenRefreshFailure(context);
            }
        });
    }

    private void handleTokenRefreshFailure(Context context) {
        AuthTokenManager.clearAuthToken(context);
        // Optionally notify the user or redirect to login
        Log.w(TAG, "Token refresh failed, user session cleared. Please log in again.");
        // Example: Intent intent = new Intent(context, LoginActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(intent);
    }
}