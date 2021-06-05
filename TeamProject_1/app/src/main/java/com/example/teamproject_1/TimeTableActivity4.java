package com.example.teamproject_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class TimeTableActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table4);
        LinearLayout linearLayout_parent = new LinearLayout(this);
        linearLayout_parent.setOrientation(LinearLayout.VERTICAL);
    }
}