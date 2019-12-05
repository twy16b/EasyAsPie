package edu.fsu.cs.easyaspie;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimerReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        int stepNumber = bundle.getInt("stepNumber");
        String recipeName = bundle.getString("recipeName");
        long notificationId = bundle.getLong("notificationId");

        // Toast.makeText(context, recipeName, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, TimerRingService.class);
        i.putExtra("stepNumber", stepNumber);
        i.putExtra("recipeName", recipeName);
        i.putExtra("notificationId", notificationId);
        context.startService(i);

    }


}