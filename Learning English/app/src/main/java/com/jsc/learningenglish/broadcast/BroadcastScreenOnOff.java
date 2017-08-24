package com.jsc.learningenglish.broadcast;

import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jsc.learningenglish.feature.Contants;
import com.jsc.learningenglish.feature.TinyDB;
import com.jsc.learningenglish.feature.utils;
import com.jsc.learningenglish.service.HeadService;


/**
 * Created by ADMIN on 6/16/2016.
 */
public class BroadcastScreenOnOff extends BroadcastReceiver {
    public static boolean broadCastRegistered = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        broadCastRegistered = true;
        // TODO Auto-generated method stub


        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            if (HeadService.HeadLayerRunning == false) {
                Intent i = new Intent(context, HeadService.class);
                i.putExtra(Contants.KEY_LEARNING, false);
                context.startService(i);
            }
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Log.i("[BroadcastReceiver]", "Screen OFF");
        }
    }
}
