package com.example.teamproject_1;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmIntentService extends IntentService {
    public final int NOTIFICATION_ID = 1001;
    public AlarmIntentService() {
        super("AlarmIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        int id = intent.getIntExtra("id", 0);
        Intent intent1 = new Intent(this, timetableActivity2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent1, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("알림 타이틀")
                .setContentText("알림 서브텍스트")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
