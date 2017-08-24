package com.jsc.learningenglish.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.jsc.learningenglish.feature.Contants;
import com.jsc.learningenglish.feature.TinyDB;
import com.jsc.learningenglish.service.HeadService;
import com.jsc.learningenglish.service.StartBroadCastScreenOnOff;


/**
 * Created by ADMIN on 6/14/2016.
 */
public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent launchIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent mAlarmIntent = PendingIntent.getBroadcast(context, 0, launchIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = Contants.TIME_OPEN_DIALOG;
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval, interval,
                mAlarmIntent);

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, HeadService.class));
            context.startService(new Intent(context, StartBroadCastScreenOnOff.class));
        }
    }
}
