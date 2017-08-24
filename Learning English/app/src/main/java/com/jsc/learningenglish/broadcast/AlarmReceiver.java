package com.jsc.learningenglish.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import com.jsc.learningenglish.feature.Contants;
import com.jsc.learningenglish.service.HeadService;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public static int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wl.acquire();

        if (HeadService.HeadLayerRunning == false) {
            Intent i = new Intent(context, HeadService.class);
            i.putExtra(Contants.KEY_LEARNING, true);
            context.startService(i);
        }

        wl.release();
    }

}
