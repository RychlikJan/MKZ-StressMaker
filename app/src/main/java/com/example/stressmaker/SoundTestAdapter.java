package com.example.stressmaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SoundTestAdapter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioPlay.playAudio2(context,1);
    }
}