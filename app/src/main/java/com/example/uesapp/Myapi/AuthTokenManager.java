package com.example.uesapp.Myapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Utility class to manage authentication tokens using SharedPreferences.
 * Handles saving, retrieving, and clearing auth tokens and refresh tokens.
 * All operations are thread-safe and use the application context to avoid memory leaks.
 */
public class AuthTokenManager {
    private static final String PREFS_NAME = "AuthPrefs";
    private static final String TOKEN_KEY = "auth_token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";
    private static final String TAG = "AuthTokenManager";

    /**
     * Saves the authentication token to SharedPreferences.
     * Removes "Bearer " prefix if present and validates the token is non-empty.
     *
     * @param context The application context (caller should pass a valid context)
     * @param token   The authentication token to save (must not be null or empty)
     * @throws IllegalArgumentException if the context is null or the token is invalid
     */
    public static void saveAuthToken(Context context, String token) {
        if (context == null) {
            Log.e(TAG, "Failed to save auth token: Context is null");
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (token == null || token.trim().isEmpty()) {
            Log.e(TAG, "Failed to save auth token: Token is null or empty");
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        String tokenToSave = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        if (tokenToSave.isEmpty()) {
            Log.e(TAG, "Failed to save auth token: Token is invalid after processing");
            throw new IllegalArgumentException("Token is invalid after processing");
        }

        Context appContext = context.getApplicationContext();
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(TOKEN_KEY, tokenToSave)
                .apply();
        Log.d(TAG, "Saved auth token: " + tokenToSave);
    }

    /**
     * Retrieves the authentication token from SharedPreferences.
     *
     * @param context The application context
     * @return The stored authentication token, or null if not found or empty
     */
    public static String getAuthToken(Context context) {
        if (context == null) {
            Log.w(TAG, "Cannot retrieve auth token: Context is null");
            return null;
        }
        Context appContext = context.getApplicationContext();
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = prefs.getString(TOKEN_KEY, null);
        if (token != null && !token.trim().isEmpty()) {
            Log.d(TAG, "Retrieved auth token: " + token);
            return token;
        }
        Log.d(TAG, "No valid auth token found");
        return null;
    }

    /**
     * Saves the refresh token to SharedPreferences.
     *
     * @param context      The application context
     * @param refreshToken The refresh token to save (must not be null or empty)
     * @throws IllegalArgumentException if the context is null or the refresh token is invalid
     */
    public static void saveRefreshToken(Context context, String refreshToken) {
        if (context == null) {
            Log.e(TAG, "Failed to save refresh token: Context is null");
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            Log.e(TAG, "Failed to save refresh token: Refresh token is null or empty");
            throw new IllegalArgumentException("Refresh token cannot be null or empty");
        }

        Context appContext = context.getApplicationContext();
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(REFRESH_TOKEN_KEY, refreshToken.trim())
                .apply();
        Log.d(TAG, "Saved refresh token: " + refreshToken);
    }

    /**
     * Retrieves the refresh token from SharedPreferences.
     *
     * @param context The application context
     * @return The stored refresh token, or null if not found or empty
     */
    public static String getRefreshToken(Context context) {
        if (context == null) {
            Log.w(TAG, "Cannot retrieve refresh token: Context is null");
            return null;
        }
        Context appContext = context.getApplicationContext();
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = prefs.getString(REFRESH_TOKEN_KEY, null);
        if (token != null && !token.trim().isEmpty()) {
            Log.d(TAG, "Retrieved refresh token: " + token);
            return token;
        }
        Log.d(TAG, "No valid refresh token found");
        return null;
    }

    /**
     * Clears both the authentication token and refresh token from SharedPreferences.
     *
     * @param context The application context
     */
    public static void clearAuthToken(Context context) {
        if (context == null) {
            Log.w(TAG, "Cannot clear tokens: Context is null");
            return;
        }
        Context appContext = context.getApplicationContext();
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .remove(TOKEN_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .apply();
        Log.d(TAG, "Cleared auth and refresh tokens");
    }

    /**
     * Checks if the user is logged in by verifying the presence of a non-empty auth token.
     *
     * @param context The application context
     * @return True if a valid auth token exists, false otherwise
     */
    public static boolean isLoggedIn(Context context) {
        boolean loggedIn = getAuthToken(context) != null;
        Log.d(TAG, "User logged in: " + loggedIn);
        return loggedIn;
    }
}