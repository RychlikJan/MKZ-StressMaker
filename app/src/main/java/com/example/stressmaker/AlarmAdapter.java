package com.example.stressmaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;




public class AlarmAdapter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioPlay.playAudio(context,1);
    }
}