package com.example.uesapp.Myapi;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenRequest {
    @SerializedName("refresh_token")
    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}