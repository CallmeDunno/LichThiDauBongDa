package com.example.lichthidaubongda;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notifyIntent = new Intent(context, MainActivity.class);
// Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Notification.CHANNEL_ID)
                .setContentTitle("Thông báo")
                .setContentText("Trận đấu sắp diễn ra")
                .setSmallIcon(R.drawable.ball)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }
}
