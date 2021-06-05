package com.example.teamproject_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class editTimeTableActivity extends AppCompatActivity implements View.OnClickListener {
    private AdView testAdView2;
    EditText edit_1, edit_3, edit_4, edit_5_1, edit_5_2;
    Spinner  spinner_2_1, spinner_2_2;
    RadioGroup rg;
    RadioButton rb;
    boolean isRbChecked = false;
    Button btn01, btn02;
    String ID;
    List<String> spinnerArray;
    int tv_firstNum = 0, tv_lastNum = 0;
    TextView tv_title;
    String tv_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_table);
        tv_title = (TextView) findViewById(R.id.textView7);
        spinnerArray = new ArrayList<String>();
        for(int i = 1; i < 10; i++) {
            spinnerArray.add(i + "교시");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            rb = (RadioButton)findViewById(checkedId);
            isRbChecked = true;
        });
        edit_1 = (EditText)findViewById(R.id.autoCompleteTextView3);
        spinner_2_1 = (Spinner)findViewById(R.id.spinner2);
        spinner_2_2 = (Spinner)findViewById(R.id.spinner3);
        edit_3 = (EditText)findViewById(R.id.editText1);
        edit_4 = (EditText)findViewById(R.id.editText2);
        edit_5_1 = (EditText)findViewById(R.id.editText3);
        edit_5_2 = (EditText)findViewById(R.id.editText4);
        testAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        testAdView2.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        btn01 = (Button)findViewById(R.id.button5);
        btn01.setOnClickListener(this);
        btn02 = (Button)findViewById(R.id.button6);
        btn02.setOnClickListener(this::deleteOnClick);
        ID = getIntent().getStringExtra("ResID");
        tv_num = ID.replaceAll("[^0-9]", "").trim();
        tv_lastNum = Integer.parseInt(tv_num);
        while(tv_lastNum > 10) {
            tv_lastNum = tv_lastNum - 10;
            tv_firstNum = tv_firstNum + 1;
        }
        char[] day = {'월', '화', '수', '목', '금'};
        tv_title.setText(tv_title.getText() + "(" + day[tv_firstNum - 1] +")");
        spinner_2_1.setAdapter(adapter);
        spinner_2_1.setSelection(tv_lastNum - 1);
        spinner_2_2.setAdapter(adapter);
        spinner_2_2.setSelection(tv_lastNum - 1);
        String filename = tv_num + "_table_data.json";
        StringBuffer sb = new StringBuffer();
        inputStream(sb, filename);
        try {
            JSONObject jsonObject = new JSONObject(String.valueOf(sb));
            RadioButton radioButton1 = (RadioButton)findViewById(R.id.radioButton2);
            radioButton1.setChecked(Boolean.parseBoolean(jsonObject.getString("isMajor")));
            RadioButton radioButton2 = (RadioButton)findViewById(R.id.radioButton);
            radioButton2.setChecked(!Boolean.parseBoolean(jsonObject.getString("isMajor")));
            isRbChecked = true;
            edit_1.setText(jsonObject.getString("subject"));
            int file_num = Integer.parseInt(jsonObject.getString("number"));
            spinner_2_1.setSelection(file_num - 1);
            spinner_2_2.setSelection(file_num - 1);
            edit_3.setText(jsonObject.getString("professor"));
            edit_4.setText(jsonObject.getString("book"));
            edit_5_1.setText(jsonObject.getString("building"));
            edit_5_2.setText(jsonObject.getString("room"));
            //+ 나중에: 1~3교시에서 2~4교시로 싹 다 옮길 때, 1교시 수업 삭제할지 물어보기
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOnClick(View v) {
        deleteFile(String.valueOf(tv_firstNum) + tv_lastNum + "_table_data.json");
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    public void cancelOnClick(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {
        StringBuffer sb = new StringBuffer();
        String s;
        int sp1 = Integer.parseInt(spinner_2_1.getSelectedItem().toString().substring(0, 1));
        int sp2 = Integer.parseInt(spinner_2_2.getSelectedItem().toString().substring(0, 1));
        int set_length = sp2 - sp1;
        boolean isNotNone = false;
        boolean isMajor = true;
        if(isRbChecked) {
            isMajor = rb.getText().toString().equals("전공");
        }
        String subject = edit_1.getText().toString();
        String professor = edit_3.getText().toString();
        String book = edit_4.getText().toString();
        String building = edit_5_1.getText().toString();
        String room = edit_5_2.getText().toString();
        if(isRbChecked && set_length >= 0 && subject.length() != 0 && professor.length() != 0 && book.length() != 0 && building.length() != 0 && room.length() != 0) {
            isNotNone = true;
        }
        int edit_1_length = 15;
        int edit_5_length = 6;
        if((s = edit_1.getText().toString()).length() > edit_1_length) {
            s = s.substring(0, 15);
            s = s + "...";
        }
        sb.append(s + " ");
        sb.append("\n");
        if((s=edit_5_1.getText().toString()).length() > edit_5_length)
        {
            s = s.substring(0, 6);
            s = s + "...";
        }
        sb.append(s + " ");
        if((s=edit_5_2.getText().toString()).length() > edit_5_length)
        {
            s = s.substring(0, 6);
            s = s + "...";
        }
        sb.append(s);
        String res = sb.toString();
        if(!isRbChecked){
            Toast.makeText(getApplicationContext(), "전공, 교양 중 하나를 선택해주세요", Toast.LENGTH_SHORT).show();
        }
        else if(set_length < 0) {
            Toast.makeText(getApplicationContext(), "종료 시간이 시작 시간보다 빠릅니다.", Toast.LENGTH_SHORT).show();
        }
        else if(subject.length() == 0) {
            Toast.makeText(getApplicationContext(), "강의 과목 이름을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(professor.length() == 0) {
            Toast.makeText(getApplicationContext(), "교수님 성함을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(book.length() == 0) {
            Toast.makeText(getApplicationContext(), "교재명을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(building.length() == 0) {
            Toast.makeText(getApplicationContext(), "건물명을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(room.length() == 0) {
            Toast.makeText(getApplicationContext(), "강의실을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if(isNotNone) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", res);
            resultIntent.putExtra("length", set_length);
            resultIntent.putExtra("spinner1", sp1);
            setResult(RESULT_OK,resultIntent);
            finish();
            Note note = new Note();
            note.setIsMajor(String.valueOf(isMajor));
            note.setSubject(subject);
            note.setProfessor(professor);
            note.setBook(book);
            note.setBuilding(building);
            note.setRoom(room);
            for(int i = sp1; i <= sp2; i++) {
                note.setFirstToLast(String.valueOf(i));
                String filename = tv_firstNum + String.valueOf(i) + "_table_data.json";
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, MODE_PRIVATE);
                    outputStream.write(JsonUtil.toJson(note).getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 일단 값 보내고, 시간이 증가하면, 칸도 증가하게(gone으로 다음칸 삭제, 선택 칸 weight 증가)
    }
    public void inputStream(StringBuffer sb, String filename) {
        InputStream in;
        BufferedReader reader;
        try {
            in = openFileInput(filename);
        }
        catch (FileNotFoundException e) {
            in = getResources().openRawResource(R.raw.timetable_data);
        }
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s;
            while((s=reader.readLine()) != null) sb.append(s);
        } catch (IOException e) {
            Log.e("오류", e.getMessage());
        }
    }
}