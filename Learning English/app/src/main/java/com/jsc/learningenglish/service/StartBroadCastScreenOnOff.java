package com.jsc.learningenglish.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import com.jsc.learningenglish.broadcast.BroadcastScreenOnOff;


/**
 * Foreground service. Creates a head view.
 * The pending intent allows to go back to the settings activity.
 */
public class StartBroadCastScreenOnOff extends Service {

    private static boolean serviceStartBroadCastScreenOnOffRunning = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (BroadcastScreenOnOff.broadCastRegistered == false) {
            System.out.println("register broadcast");
            BroadcastScreenOnOff.broadCastRegistered = true;
            BroadcastScreenOnOff broadcast = new BroadcastScreenOnOff();
            registerReceiver(broadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
            registerReceiver(broadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        }
        StartBroadCastScreenOnOff.setStateRunning();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("destroy start");
        super.onDestroy();
    }

    public static boolean isRunning() {
        return StartBroadCastScreenOnOff.serviceStartBroadCastScreenOnOffRunning;
    }

    private static void setStateRunning() {
        StartBroadCastScreenOnOff.serviceStartBroadCastScreenOnOffRunning = true;
    }
}
