package edu.fsu.cs.easyaspie;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimerRingService extends Service implements MediaPlayer.OnPreparedListener {
    MediaPlayer mediaPlayer;

    public TimerRingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_clock_5);
        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        Bundle bundle = intent.getExtras();

        int stepNumber = bundle.getInt("stepNumber");
        String recipeName = bundle.getString("recipeName");
        long notificationId = bundle.getLong("notificationId");

        Intent i = new Intent(this, SilenceTimer.class);
        i.putExtra("stepNumber" , stepNumber);
        i.putExtra("recipeName", recipeName);
        i.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) notificationId, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Easy as Pie")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle(recipeName)
                .setContentText("Time's Up!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_action_stat_reply, "Silence" , pendingIntent);



        startForeground((int) notificationId, builder.build());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onPrepared(MediaPlayer player) {

    }
}
