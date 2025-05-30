package com.example.uesapp.Myapi;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class to manage authentication tokens using SharedPreferences.
 * Handles saving, retrieving, and clearing auth tokens and refresh tokens.
 */
public class AuthTokenManager {
    private static final String PREFS_NAME = "AuthPrefs";
    private static final String TOKEN_KEY = "auth_token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * Saves the authentication token to SharedPreferences.
     * Removes "Bearer " prefix if present to store only the token value.
     *
     * @param context The application context
     * @param token   The authentication token to save
     */
    public static void saveAuthToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String tokenToSave = token.startsWith("Bearer ") ? token.substring(7) : token;
        prefs.edit()
                .putString(TOKEN_KEY, tokenToSave)
                .apply();
    }

    /**
     * Retrieves the authentication token from SharedPreferences.
     *
     * @param context The application context
     * @return The stored authentication token, or null if not found
     */
    public static String getAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, null);
    }

    /**
     * Saves the refresh token to SharedPreferences.
     *
     * @param context      The application context
     * @param refreshToken The refresh token to save
     */
    public static void saveRefreshToken(Context context, String refreshToken) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(REFRESH_TOKEN_KEY, refreshToken)
                .apply();
    }

    /**
     * Retrieves the refresh token from SharedPreferences.
     *
     * @param context The application context
     * @return The stored refresh token, or null if not found
     */
    public static String getRefreshToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(REFRESH_TOKEN_KEY, null);
    }

    /**
     * Clears both the authentication token and refresh token from SharedPreferences.
     *
     * @param context The application context
     */
    public static void clearAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .remove(TOKEN_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .apply();
    }

    /**
     * Checks if the user is logged in by verifying the presence of an auth token.
     *
     * @param context The application context
     * @return True if an auth token exists, false otherwise
     */
    public static boolean isLoggedIn(Context context) {
        return getAuthToken(context) != null;
    }
}