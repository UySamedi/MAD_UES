package com.example.uesapp.Myapi;

import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("api/registrations")
    Call<RegistrationResponse> createRegistration(
            @Part("user_id") RequestBody userId,
            @Part("department_id") RequestBody departmentId,
            @Part("firstname") RequestBody firstName,
            @Part("lastname") RequestBody lastName,
            @Part("gender") RequestBody gender,
            @Part("dob") RequestBody dob,
            @Part("address") RequestBody address,
            @Part("phone") RequestBody phone,
            @Part("phonenumber") RequestBody phoneNumber,
            @Part("fathername") RequestBody fatherName,
            @Part("father_job") RequestBody fatherJob,
            @Part("father_alive") RequestBody fatherAlive,
            @Part("mothername") RequestBody motherName,
            @Part("mother_job") RequestBody motherJob,
            @Part("mother_alive") RequestBody motherAlive,
            @Part("education_name") RequestBody educationName,
            @Part("education_date") RequestBody educationDate,
            @Part("education_location") RequestBody educationLocation,
            @Part("education_grade") RequestBody educationGrade,
            @Part MultipartBody.Part picture,
            @Part MultipartBody.Part educationImage
    );

    @GET("departments")
    Call<DepartmentResponse> getDepartments();

    @POST("api/register")
    Call<ApiResponse> register(@Body RegisterRequest registerRequest);

    @POST("api/login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);

    @GET("api/profile")
    Call<ProfileResponse> getProfile();

    @POST("api/refresh-token")
    Call<ApiResponse> refreshToken(@Body RefreshTokenRequest request);

    @GET("api/registrations")
    Call<List<RegistrationResponse>> getAllRegistrations();
}