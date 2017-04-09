package com.example.cabanni.lokalpatriot_brackel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by cabanni on 03.01.17.
 *startet service insofern das Smartphone neu gebootet wurde
 */

public class BootCompleteReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent startServiceIntent = new Intent(context, NotificationService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, startServiceIntent, 0);
            Calendar calender = Calendar.getInstance();
            calender.setTimeInMillis(System.currentTimeMillis()); // ohne Verzögerung
            alarmManager.setRepeating(AlarmManager.RTC, calender.getTimeInMillis(),
                    (1000 * 60 * 10), pendingIntent);

        }

    }
}
