package edu.fsu.cs.easyaspie;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StartTimerService extends IntentService {

    int tick = 0;

    public StartTimerService() {
        super("StartTimerService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("StartTimerService", "onHandleIntent: Started Service");
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            long time = bundle.getLong("time");
            String recipeName = bundle.getString("recipeName");
            int recipeID = bundle.getInt("recipeID");
            int stepNumber = bundle.getInt("stepNumber");

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();

            long currtime = calendar.getTimeInMillis();
            long alarmtime = currtime + time*1000;

            Intent i = new Intent(this, TimerReciever.class);

            i.putExtra("recipeName", recipeName);
            i.putExtra("recipeID", recipeID);
            i.putExtra("stepNumber", stepNumber);
            i.putExtra("notificationId", currtime);

            //creating a pending intent using the intent
            PendingIntent pi = PendingIntent.getBroadcast(this, (int) currtime, i, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, alarmtime, pi);
            }
            else {
                am.set(AlarmManager.RTC_WAKEUP, alarmtime, pi);
            }

            Calendar datetime = Calendar.getInstance();
            datetime.setTimeInMillis(alarmtime);

            int hour = datetime.get(Calendar.HOUR);
            if (hour == 0) {
                hour = 12;
            }

            String minutestr;
            int minute = datetime.get(Calendar.MINUTE);
            if (minute < (int) 10) {
                minutestr = "0" + minute;
            }
            else {
                minutestr = "" + minute;
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), "Easy as Pie")
                    .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                    .setContentTitle(recipeName)
                    .setContentText("Step " + (stepNumber + 1) + ": Timer rings at " + hour + ":" + minutestr)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setOngoing(false)
                    .setOnlyAlertOnce(true);

            createNotificationChannel();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());

            notificationManager.notify((int) currtime, builder.build());
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Easy as Pie";
            String description = "Timers";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Easy as Pie", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
