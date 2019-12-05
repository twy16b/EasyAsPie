package edu.fsu.cs.easyaspie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SilenceTimer extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, TimerRingService.class);
        i.setAction("Stop");
        context.startService(i);
        context.stopService(i);
        String recipeName = intent.getStringExtra("recipeName");
        int recipeID = intent.getIntExtra("recipeID", 0);
        int stepNumber = intent.getIntExtra("stepNumber", 1);

        Intent stepIntent = new Intent(context, RecipeSteps.class);
        stepIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        stepIntent.putExtra("recipeName", recipeName);
        stepIntent.putExtra("recipeID", recipeID);
        stepIntent.putExtra("stepNumber", stepNumber);
        context.startActivity(stepIntent);
    }
}
