package com.example.timemanagement.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.example.timemanagement.R;

public class MyApplication extends Application {
    //    public static final String CHANNEL_ID_1 = "CHANNEL 1";
//    public static final String CHANNEL_ID_2 = "CHANNEL 2";
    public static final String CHANNEL_ID = "CHANNEL_SERVICE";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//            AudioAttributes attributes= new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
            //config channel 1
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_1, name, importance);
//            channel.setDescription(description);
//            //config channel 2
//            CharSequence name_2 = getString(R.string.channel_name_2);
//            String description_2 = getString(R.string.channel_description_2);
//            int importance_2 = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel_2 = new NotificationChannel(CHANNEL_ID_2, name_2, importance_2);
//            channel_2.setDescription(description_2);
//            channel_2.setSound(uri,attributes);
            //config channel service
            CharSequence name_service = getString(R.string.channel_name_service);
            String description_service = getString(R.string.channel_description_service);
            int importance_service = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel_service = new NotificationChannel(CHANNEL_ID, name_service, importance_service);
            channel_service.setDescription(description_service);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            notificationManager.createNotificationChannel(channel_2);
            notificationManager.createNotificationChannel(channel_service);
        }
    }
}
