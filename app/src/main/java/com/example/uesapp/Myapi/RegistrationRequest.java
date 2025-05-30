package com.example.uesapp.Myapi;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {
    @SerializedName("user_id")
    private int userId;

    @SerializedName("department_id")
    private String departmentId;

    @SerializedName("personal_detail")
    private PersonalDetail personalDetail;

    @SerializedName("parent_detail")
    private ParentDetail parentDetail;

    @SerializedName("education_detail")
    private EducationDetail educationDetail;

    public RegistrationRequest(int userId, String departmentId, PersonalDetail personalDetail,
                               ParentDetail parentDetail, EducationDetail educationDetail) {
        this.userId = userId;
        this.departmentId = departmentId;
        this.personalDetail = personalDetail;
        this.parentDetail = parentDetail;
        this.educationDetail = educationDetail;
    }

    public int getUserId() { return userId; }
    public String getDepartmentId() { return departmentId; }
    public PersonalDetail getPersonalDetail() { return personalDetail; }
    public ParentDetail getParentDetail() { return parentDetail; }
    public EducationDetail getEducationDetail() { return educationDetail; }

    public static class PersonalDetail {
        @SerializedName("firstname")
        private String firstName;
        @SerializedName("lastname")
        private String lastName;
        @SerializedName("gender")
        private String gender;
        @SerializedName("dob")
        private String dob;
        @SerializedName("address")
        private String address;
        @SerializedName("phone")
        private String phone;
        @SerializedName("phonenumber")
        private String phoneNumber;

        public PersonalDetail(String firstName, String lastName, String gender, String dob,
                              String address, String phone, String phoneNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.dob = dob;
            this.address = address;
            this.phone = phone;
            this.phoneNumber = phoneNumber;
        }
    }

    public static class ParentDetail {
        @SerializedName("fathername")
        private String fatherName;
        @SerializedName("father_job")
        private String fatherJob;
        @SerializedName("father_alive")
        private int fatherAlive;
        @SerializedName("mothername")
        private String motherName;
        @SerializedName("mother_job")
        private String motherJob;
        @SerializedName("mother_alive")
        private int motherAlive;

        public ParentDetail(String fatherName, String fatherJob, int fatherAlive,
                            String motherName, String motherJob, int motherAlive) {
            this.fatherName = fatherName;
            this.fatherJob = fatherJob;
            this.fatherAlive = fatherAlive;
            this.motherName = motherName;
            this.motherJob = motherJob;
            this.motherAlive = motherAlive;
        }
    }

    public static class EducationDetail {
        @SerializedName("education_name")
        private String educationName;
        @SerializedName("education_date")
        private String educationDate;
        @SerializedName("education_location")
        private String educationLocation;
        @SerializedName("education_grade")
        private String educationGrade;

        public EducationDetail(String educationName, String educationDate, String educationLocation,
                               String educationGrade) {
            this.educationName = educationName;
            this.educationDate = educationDate;
            this.educationLocation = educationLocation;
            this.educationGrade = educationGrade;
        }
    }
}