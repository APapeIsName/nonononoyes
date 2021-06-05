/*
package com.example.teamproject_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class TimeTableActivity3 extends AppCompatActivity{
    private AdView testAdView;
    private static final int START_ACTIVITY = 1;
    int tvID;
    TextView tv_editTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table3);

        for(int i=1; i<6; i++) {
            for(int j=1; j<10; j++)
            {
                String strID = "tv_" + i + "_" + j;
                String strPackage = this.getPackageName();
                int resID = getResources().getIdentifier(strID,"id", strPackage);
                ((TextView)findViewById(resID)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvID = v.getId();
                        tv_editTable = (TextView)findViewById(tvID);
                        Intent intent4 = new Intent(TimeTableActivity3.this, editTimeTableActivity.class);
                        intent4.putExtra("ResID", tvID);
                        startActivityForResult(intent4, START_ACTIVITY);
                    }
                });
            }
        }
        //testAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        testAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        tv_editTable = (TextView)findViewById(tvID);
        tv_editTable.setText();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tv_editTable = (TextView)findViewById(tvID);
        if(requestCode == START_ACTIVITY) {
            if(resultCode != RESULT_OK) {
                tv_editTable.setText("오류");
            }
            else if(resultCode == RESULT_OK) {
                String res = data.getExtras().getString("result");
                tv_editTable.setText(res);
            }
        }
    }
}
 */