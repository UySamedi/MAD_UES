package com.example.uesapp.Myapi;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthTokenManager {
    private static final String PREFS_NAME = "auth_prefs";
    private static final String TOKEN_KEY = "auth_token";

    public static void saveAuthToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(TOKEN_KEY, token.startsWith("Bearer ") ? token.substring(7) : token)
                .apply();
    }

    public static String getAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, null);
    }

    public static void clearAuthToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(TOKEN_KEY).apply();
    }

    public static boolean isLoggedIn(Context context) {
        return getAuthToken(context) != null;
    }
}