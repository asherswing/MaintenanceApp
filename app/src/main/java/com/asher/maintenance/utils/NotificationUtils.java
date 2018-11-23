package com.asher.maintenance.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.asher.maintenance.R;

public class NotificationUtils {

    private static final String CHANNEL_ID_PROGRESS = "PROGRESS";
    private static final String CHANNEL_DESC_PROGRESS = "FORM_IN_PROGRESS_CHANNEL";
    public static final int NOTIFICATION_ID_PROGRESS = 101;

    public static void showNotificationFormInProgress(Context context){
        NotificationManager notificationManager =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager.class);
        }else{
            notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (notificationManager == null) return;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context, CHANNEL_ID_PROGRESS)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.title_form_progress))
                .setContentText(context.getString(R.string.text_form_progress))
             //   .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_PROGRESS,
                    CHANNEL_ID_PROGRESS,
                    importance
            );
            channel.setDescription(CHANNEL_DESC_PROGRESS);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this


            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID_PROGRESS, mBuilder.build());
    }

   /* private void getAppPendingIntent(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    }*/

   private void closeProgressNotification(Context context){
       NotificationManager notificationManager =
               null;
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
           notificationManager = context.getSystemService(NotificationManager.class);
       }else{
           notificationManager =
                   (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       }
       if (notificationManager == null) return;
       notificationManager.cancel(NOTIFICATION_ID_PROGRESS);

   }

    public static void showNotificationFormCompleted(Context context, String title){
        NotificationManager notificationManager =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager.class);
        }else{
            notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (notificationManager == null) return;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context, CHANNEL_ID_PROGRESS)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.title_form_completed))
                .setContentText(title)
                //   .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_PROGRESS,
                    CHANNEL_ID_PROGRESS,
                    importance
            );
            channel.setDescription(CHANNEL_DESC_PROGRESS);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this


            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID_PROGRESS, mBuilder.build());
    }

    public static void showReminder(Context context, String title, String body){
        NotificationManager notificationManager =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager.class);
        }else{
            notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (notificationManager == null) return;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context, CHANNEL_ID_PROGRESS)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
                //   .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID_PROGRESS,
                    CHANNEL_ID_PROGRESS,
                    importance
            );
            channel.setDescription(CHANNEL_DESC_PROGRESS);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this


            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID_PROGRESS, mBuilder.build());
    }
}
