package com.example.stressmaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

class StressLevel{
    MediaPlayer mediaPlayer;
    private static final StressLevel ourInstance = new StressLevel();
    private int lvl;
    static StressLevel getInstance() {
        return ourInstance;
    }

    private StressLevel() {
    }

    public void setLvl(int lvl){
        this.lvl = lvl;
    }
    public void increaseLvl(int plus){
        this.lvl = this.lvl + plus;
    }

    public int getLvl(){
        return this.lvl;
    }

}
