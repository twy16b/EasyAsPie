package edu.fsu.cs.easyaspie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

        Toast.makeText(context, recipeName, Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Easy as Pie")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle(recipeName)
                .setContentText("Time's Up!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify((int) notificationId,  builder.build());

    }
}
