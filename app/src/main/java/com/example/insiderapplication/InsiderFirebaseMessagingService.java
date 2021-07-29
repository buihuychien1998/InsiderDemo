package com.example.insiderapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.useinsider.insider.Insider;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class InsiderFirebaseMessagingService extends FirebaseMessagingService {
    private static final String NOTIFICATION_CHANNEL_ID = "Insider";
    private static final CharSequence NOTIFICATION_CHANNEL_NAME = "Insider Name";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Context context = getApplicationContext();
        if (remoteMessage.getData().containsKey("source")
            && "Insider".equals(remoteMessage.getData().get("source"))) {
            Insider.Instance.handleFCMNotification(context, remoteMessage);
        }
        Log.d("TAG", "onMessageReceived: ");
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build();
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.sample); //Here is FILE_NAME is the name of file that you want to play
        NotificationManager notificationManager = (NotificationManager)
            context.getSystemService(NOTIFICATION_SERVICE);
        if (!Objects.equals(null, remoteMessage.getNotification())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setSound(sound, audioAttributes);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            notificationBuilder.setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setTicker(remoteMessage.getNotification().getTitle())
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(sound)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody());
            notificationManager.notify(1, notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("TAG", "onNewToken: ");
    }
}