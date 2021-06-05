package com.example.teamproject_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.gridlayout.widget.GridLayout;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class timetableActivity2 extends AppCompatActivity {
    private static final int START_ACTIVITY = 1;
    TextView tv_editTable;
    int tvID;
    GridLayout table_grid;
    String tv_num;
    String strPackage;
    private int hour, minute;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable2);
        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        table_grid = (androidx.gridlayout.widget.GridLayout)findViewById(R.id.tt_grid);
        try {
            JSONObject jsonObject = new JSONObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i=1; i<6; i++) {
            for (int j = 1; j < 10; j++) {
                String strID = "tv_" + i + "_" + j;
                strPackage = this.getPackageName();
                int resID = getResources().getIdentifier(strID, "id", strPackage);
                ((TextView) findViewById(resID)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvID = v.getId();
                        tv_num = strID.replaceAll("[^0-9]", "").trim();
                        tv_editTable = (TextView) findViewById(tvID);
                        Intent intent4 = new Intent(timetableActivity2.this, editTimeTableActivity.class);
                        intent4.putExtra("ResID", strID);
                        startActivityForResult(intent4, START_ACTIVITY);
                    }
                });
            }
        }
        setTableFromFile();
        createNotificationChannel();
        setAlarm();
        // 이후에 추가(시간에 따라 칸이 증가)
        /*GridLayout.LayoutParams params = new GridLayout.LayoutParams(table_grid.getLayoutParams());
        params.rowSpec = GridLayout.spec(2,2);
        params.columnSpec = GridLayout.spec(2, 1);
        tv_editTable.setLayoutParams(params);
        tv_editTable.setVisibility(View.VISIBLE);*/
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

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK)-2;
        if(i>=1 && i<=5) {
            for (int j = 1; j <= 9; j++) {
                int requestcode = Integer.parseInt(String.valueOf(i) + j);
                File file = new File(getFilesDir().getAbsolutePath() + "/" + requestcode + "_table_data.json");
                if (file.exists()) {
                    Intent receiverIntent = new Intent(timetableActivity2.this, AlarmReceiver.class);
                    receiverIntent.putExtra("id", requestcode);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(timetableActivity2.this, requestcode, receiverIntent, 0);
                    hour = 16;
                    minute = 0;
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    long selectTime=calendar.getTimeInMillis();
                    long currenTime=System.currentTimeMillis();

                    long intervalDay = 7 * 24 * 60 * 60 * 1000;
                    if(currenTime>selectTime){
                        selectTime += intervalDay;
                    }
                    alarmManager.set(AlarmManager.RTC_WAKEUP, selectTime, pendingIntent);
                } else {
                    Intent receiverIntent = new Intent(timetableActivity2.this, AlarmReceiver.class);
                    receiverIntent.putExtra("id", requestcode);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(timetableActivity2.this, requestcode, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }

    private void setTableFromFile() {
        for(int i=1; i<6; i++) {
            for (int j = 1; j < 10; j++) {
                String strID = "tv_" + i + "_" + j;
                strPackage = this.getPackageName();
                int resID = getResources().getIdentifier(strID, "id", strPackage);
                StringBuffer sb = new StringBuffer();
                StringBuffer resb = new StringBuffer();
                String filename = String.valueOf(i) + j + "_table_data.json";
                inputStream(sb, filename);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(sb));
                    String s="";
                    String sub = jsonObject.getString("subject");
                    String building = jsonObject.getString("building");
                    String room = jsonObject.getString("room");
                    if((s = sub).length() > 15) {
                        s = s.substring(0, 15);
                        s = s + "...";
                    }
                    resb.append(s).append(" ");
                    resb.append("\n");
                    if((s=building).length() > 6)
                    {
                        s = s.substring(0, 6);
                        s = s + "...";
                    }
                    resb.append(s).append(" ");
                    if((s=room).length() > 6)
                    {
                        s = s.substring(0, 6);
                        s = s + "...";
                    }
                    resb.append(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((TextView) findViewById(resID)).setText(resb.toString());
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int tv_lastNum = Integer.parseInt(tv_num);
        int tv_firstNum = 0;
        while(tv_lastNum > 10) {
            tv_lastNum = tv_lastNum - 10 ;
            tv_firstNum = tv_firstNum + 1;
        }
        if(requestCode == START_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                setAlarm();
                setTableFromFile();
            }
            else {
                Toast.makeText(getApplicationContext(), "값이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "알림설정에서의 제목";
            String description = "Oreo Version 이상을 위한 알림(알림설정에서의 설명)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("channel_id", name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}