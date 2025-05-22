package com.example.uesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uesapp.Register.LoginActivity;
import com.example.uesapp.Register.signupActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView haveAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // Initialize UI components
        btnStart = findViewById(R.id.btn_start);
        haveAcc = findViewById(R.id.have_acc);

        // Navigate to SignupActivity
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, signupActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to LoginActivity
        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
