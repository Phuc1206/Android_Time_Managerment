package com.example.timemanagement.notification;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.timemanagement.R;
import com.example.timemanagement.database.Podomoro;
import com.example.timemanagement.notification.MyApplication;
import com.example.timemanagement.views.Timer;

public class MyService extends Service {
    //    private NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onCreate() {
        super.onCreate();
//        builder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID);
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Podomoro podomoro = (Podomoro) bundle.get("podomoro");
            assert podomoro != null;
            sendNotification(podomoro);
        }
        return START_NOT_STICKY;
    }


    private void sendNotification(Podomoro podomoro) {
        Intent intent = new Intent(this, Timer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentText("Description")
                .setContentTitle("Title")
                .setSmallIcon(R.drawable.ic_cachua);
//                .setProgress(progressmax, 0, false)


//        startForeground(3,notification.build());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.notify(2, notification.build());
//        final Context context = this;
//        final Handler mainHandler = new Handler(Looper.getMainLooper());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int time = podomoro.getTime()*60*1000;
//
//                for (int progress = 0; progress < time; progress+=1000) {
//                    final int currentProgress = (int)((progress/(double)time)*100);
//                    mainHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            notification.setProgress(100, currentProgress, false)
//                                    .setContentText("Thời gian đang chạy")
//                                    .setSound(null);
//                        }
//                    });
//                    SystemClock.sleep(1000);
//                }
//
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        notification.setContentText("Done")
//                                .setContentIntent(pendingIntent)
//                                .setProgress(0, 0, false);
//
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                        notificationManagerCompat.notify(3, notification.build());
//                    }
//                });
//            }
//        }).start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
