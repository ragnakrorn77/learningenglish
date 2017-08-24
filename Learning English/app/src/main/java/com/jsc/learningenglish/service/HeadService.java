package com.jsc.learningenglish.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.jsc.learningenglish.feature.Contants;
import com.jsc.learningenglish.feature.MessageLockScreen;
import com.jsc.learningenglish.feature.utils;


/**
 * Foreground service. Creates a head view.
 * The pending intent allows to go back to the settings activity.
 */
public class HeadService extends Service {

    private final static int FOREGROUND_ID = 999;

    private MessageLockScreen mHeadLayer;
    public static boolean HeadLayerRunning = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        utils.DebugLog("zo service");
        HeadLayerRunning = true;
        logServiceStarted();
        boolean isLearning = intent.getBooleanExtra(Contants.KEY_LEARNING, true);
        mHeadLayer = new MessageLockScreen(this, isLearning);



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        HeadLayerRunning = false;
        destroyHeadLayer();
        logServiceEnded();
    }

    private void destroyHeadLayer() {
        mHeadLayer.destroy();
        mHeadLayer = null;
    }


    private void logServiceStarted() {
        //Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }

    private void logServiceEnded() {
        //Toast.makeText(this, "Service ended", Toast.LENGTH_SHORT).show();
    }
}
