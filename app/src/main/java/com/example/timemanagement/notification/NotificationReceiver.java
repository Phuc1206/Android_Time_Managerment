package com.example.timemanagement.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.timemanagement.R;
import com.example.timemanagement.views.StartPodomoroActivity;
import com.example.timemanagement.views.WeekViewActivity;

import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {
    final String CHANNEL_ID="202";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("My Action 2")){
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
                NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Channel 2",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("This is Channel 2");
                notificationManager.createNotificationChannel(channel);
            }
            Intent notificationIntent = new Intent(context, WeekViewActivity.class);

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
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setColor(colorInt)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    //    .addAction(R.drawable.baseline_done,"Mask as done" ,DonePIntent)
                    .setAutoCancel(true);
            notificationManager.notify(getNotification(),builder.build());
        }
    }

    private int getNotification() {
        int time = (int)new Date().getTime();
        return time;
    }

}
