package com.example.uesapp.Myapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepartmentResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data") // Match your API's field name for departments array
    private List<Department> departments;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}