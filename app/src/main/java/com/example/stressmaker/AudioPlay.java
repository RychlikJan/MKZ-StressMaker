package com.example.stressmaker;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.Settings;

import static android.content.Context.AUDIO_SERVICE;

public class AudioPlay {

    public static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    public static boolean isplayingAudio=false;

    public static void playAudio(Context c, int id){
        if(id == 1) {
            mediaPlayer = MediaPlayer.create(c, Settings.System.DEFAULT_RINGTONE_URI);
        }
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        if(!mediaPlayer.isPlaying())
        {
            isplayingAudio=true;
            mediaPlayer.start();
        }
    }

    public static void playAudio2(Context c, int id){
        AudioManager mgr = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        int valuess = 15;//range(0-15)
        mgr.setStreamVolume(AudioManager.STREAM_MUSIC, valuess, 0);
        if(id == 1) {
            mediaPlayer = MediaPlayer.create(c, R.raw.strela);
        }
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        if(!mediaPlayer.isPlaying())
        {
            isplayingAudio=true;
            mediaPlayer.start();
        }
    }
    public static void stopAudio(){
        isplayingAudio=false;
        mediaPlayer.stop();
    }
}