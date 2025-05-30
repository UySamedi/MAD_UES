package com.example.uesapp.Myapi;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("department_id")
    private int departmentId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("personal_detail")
    private PersonalDetail personalDetail;
    @SerializedName("parent_detail")
    private ParentDetail parentDetail;
    @SerializedName("education_detail")
    private EducationDetail educationDetail;
    @SerializedName("user")
    private User user;
    @SerializedName("department")
    private Department department;

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getDepartmentId() { return departmentId; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public PersonalDetail getPersonalDetail() { return personalDetail; }
    public ParentDetail getParentDetail() { return parentDetail; }
    public EducationDetail getEducationDetail() { return educationDetail; }
    public User getUser() { return user; }
    public Department getDepartment() { return department; }

    public static class PersonalDetail {
        @SerializedName("id")
        private int id;
        @SerializedName("registration_id")
        private int registrationId;
        @SerializedName("firstname")
        private String firstname;
        @SerializedName("lastname")
        private String lastname;
        @SerializedName("picture")
        private String picture;
        @SerializedName("gender")
        private String gender;
        @SerializedName("dob")
        private String dob;
        @SerializedName("address")
        private String address;
        @SerializedName("phone")
        private String phone;
        @SerializedName("phonenumber")
        private String phonenumber;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() { return id; }
        public int getRegistrationId() { return registrationId; }
        public String getFirstname() { return firstname; }
        public String getLastname() { return lastname; }
        public String getPicture() { return picture; }
        public String getGender() { return gender; }
        public String getDob() { return dob; }
        public String getAddress() { return address; }
        public String getPhone() { return phone; }
        public String getPhonenumber() { return phonenumber; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }
    }

    public static class ParentDetail {
        @SerializedName("id")
        private int id;
        @SerializedName("registration_id")
        private int registrationId;
        @SerializedName("fathername")
        private String fathername;
        @SerializedName("father_job")
        private String fatherJob;
        @SerializedName("father_alive")
        private int fatherAlive;
        @SerializedName("mothername")
        private String mothername;
        @SerializedName("mother_job")
        private String motherJob;
        @SerializedName("mother_alive")
        private int motherAlive;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() { return id; }
        public int getRegistrationId() { return registrationId; }
        public String getFathername() { return fathername; }
        public String getFatherJob() { return fatherJob; }
        public int getFatherAlive() { return fatherAlive; }
        public String getMothername() { return mothername; }
        public String getMotherJob() { return motherJob; }
        public int getMotherAlive() { return motherAlive; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }
    }

    public static class EducationDetail {
        @SerializedName("id")
        private int id;
        @SerializedName("registration_id")
        private int registrationId;
        @SerializedName("education_image")
        private String educationImage;
        @SerializedName("education_name")
        private String educationName;
        @SerializedName("education_date")
        private String educationDate;
        @SerializedName("education_location")
        private String educationLocation;
        @SerializedName("education_grade")
        private String educationGrade;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() { return id; }
        public int getRegistrationId() { return registrationId; }
        public String getEducationImage() { return educationImage; }
        public String getEducationName() { return educationName; }
        public String getEducationDate() { return educationDate; }
        public String getEducationLocation() { return educationLocation; }
        public String getEducationGrade() { return educationGrade; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }
    }

    public static class User {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("email_verified_at")
        private String emailVerifiedAt;
        @SerializedName("role")
        private String role;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getEmailVerifiedAt() { return emailVerifiedAt; }
        public String getRole() { return role; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }
    }

    public static class Department {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("description")
        private String description;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getImage() { return image; }
        public String getDescription() { return description; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }
    }
}