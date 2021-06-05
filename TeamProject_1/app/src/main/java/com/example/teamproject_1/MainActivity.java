package com.example.teamproject_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fOnClick(View v) {
        Intent intent1 = new Intent(this, RegisterActivity.class);
        startActivity(intent1);
    }

    public void lOnClick(View v) {
        Intent intent2 = new Intent(this, LoginActivity.class);
        startActivity(intent2);
    }
}