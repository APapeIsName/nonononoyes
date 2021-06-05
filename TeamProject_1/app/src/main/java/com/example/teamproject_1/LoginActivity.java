package com.example.teamproject_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void ttOnClick(View v) {
        Intent intent3 = new Intent(this, timetableActivity2.class);
        startActivity(intent3);
    }
}