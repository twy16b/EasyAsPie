package edu.fsu.cs.easyaspie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SilenceTimer extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, TimerRingService.class);

        context.stopService(i);

    }
}
