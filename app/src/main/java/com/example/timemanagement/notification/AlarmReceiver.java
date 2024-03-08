package com.example.timemanagement.notification;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.timemanagement.R;
import com.example.timemanagement.views.StartPodomoroActivity;
import com.example.timemanagement.views.WeekViewActivity;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    final String CHANNEL_ID="201";

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri= Settings.System.DEFAULT_ALARM_ALERT_URI;
        if (intent.getAction().equals("My Action")){
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
                NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Channel 1",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("This is Channel 1");
                channel.setSound(uri,audioAttributes);
                notificationManager.createNotificationChannel(channel);
            }
            Intent notificationIntent = new Intent(context, WeekViewActivity.class);

//            Intent DoneIntent = new Intent(context, WeekViewActivity.class);
//            DoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent DonePIntent = PendingIntent.getActivity(context,0,DoneIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(WeekViewActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE);
            String taskColor = intent.getStringExtra("Color");
            int colorInt = Color.parseColor(taskColor);
            NotificationCompat.Builder builder= new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.notifications_active)
                    .setContentTitle(intent.getStringExtra("Title"))
                    .setContentText(intent.getStringExtra("Desc"))
                    .setColor(colorInt)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setSound(uri)
                    .setVibrate(new long[] { 1000, 1000})
                    //    .addAction(R.drawable.baseline_done,"Mask as done" ,DonePIntent)
//                    .addAction(R.drawable.baseline_snooze_24,"Snooze",)
                    .setAutoCancel(true);
//            mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//            mediaPlayer.setLooping(true);
//            mediaPlayer.start();
            notificationManager.notify(getNotification(),builder.build());
        }
    }

    private int getNotification() {
        int time = (int)new Date().getTime();
        return time;
    }
}
