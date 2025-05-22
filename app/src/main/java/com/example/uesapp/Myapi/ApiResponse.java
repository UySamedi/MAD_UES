package com.example.uesapp.Myapi;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String access_token;

    @SerializedName("user") // Changed from "data" to "user" to match API response
    private Data data;

    public String getMessage() {
        return message;
    }

    public String getAccess_token() {
        if (access_token != null && !access_token.isEmpty()) {
            return access_token;
        } else if (data != null && data.getAccess_token() != null && !data.getAccess_token().isEmpty()) {
            return data.getAccess_token();
        }
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("token")
        private String access_token;

        @SerializedName("email_verified_at")
        private String emailVerifiedAt;

        @SerializedName("role")
        private String role;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getEmailVerifiedAt() {
            return emailVerifiedAt;
        }

        public void setEmailVerifiedAt(String emailVerifiedAt) {
            this.emailVerifiedAt = emailVerifiedAt;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}