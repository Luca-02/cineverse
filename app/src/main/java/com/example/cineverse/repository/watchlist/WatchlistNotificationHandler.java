package com.example.cineverse.repository.watchlist;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.view.user_watchlist.UserWatchlistActivity;

public class WatchlistNotificationHandler {

    private static final String CHANNEL_ID = "watchlistChannelId";
    private final Context context;

    public WatchlistNotificationHandler(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        CharSequence name = context.getString(R.string.your_watchlist);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        NotificationManager notificationManager =
                context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void showNotification(AbstractContent content) {
        Intent intent = new Intent(context, UserWatchlistActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(content.getName())
                .setContentText(context.getString(R.string.added_to_watch_list))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(content.getId(), builder.build());
    }

}
