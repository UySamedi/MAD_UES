# 📚 University Enrollment System - Android (Java)

This is an Android-based University Enrollment System developed using Java. It allows students to register, log in, and enroll in courses. Admins can manage course offerings, approve student applications, and monitor enrollment data.

## 📱 Features

### 👨‍🎓 Student
- Register and login using email/password
- View available courses
- Enroll in preferred courses
- Track enrollment history

### 🛠️ Admin
- Login securely as administrator
- Add/edit/delete courses
- View student enrollment requests
- Approve or reject enrollments

## 🔧 Technologies Used

- **Android Studio** – Main IDE for development  
- **Java** – Primary programming language  
- **Firebase** – For authentication and real-time database (or your backend if used)  
- **XML** – For UI layout and design  

## 📂 Project Structure

```bash
UniversityEnrollmentSystem/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/university_enrollment/
│   │   │   │       ├── activities/     # Activities like Login, Signup, Admin Dashboard
│   │   │   │       ├── models/         # Data models (e.g., User, Course)
│   │   │   │       ├── adapters/       # RecyclerView Adapters
│   │   │   │       ├── utils/          # Firebase Utils or Helpers
│   │   │   │       └── MainActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/             # XML layout files
│   │   │   │   ├── values/             # colors.xml, strings.xml, etc.
│   │   │   │   └── drawable/           # Icons, backgrounds
├── build.gradle
└── README.md

