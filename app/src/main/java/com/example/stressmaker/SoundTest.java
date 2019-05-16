package com.example.stressmaker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SoundTest extends AppCompatActivity {

    Button yes,no;
    MediaPlayer mp;
    StressLevel sr;
    int countOfAttempts;
    boolean mpplay;
    String[] songs ={"m0.mp3","m1.mp3","E","m3.mp3","m4.mp3","E","m5.mp3","m6.mp3","E","m7.mp3","8.mp3","E","E"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sound test");
        setContentView(R.layout.activity_sound_test);
        yes = findViewById(R.id.bt_yes);
        no = findViewById(R.id.bt_no);
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        sr = StressLevel.getInstance();
        setCountOfAttempts();
        showInfoDialog();

    }


    protected void showInfoDialog(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Welcome in sound test. After timer ends, the application play " +
                "a sound. Vote yes/no if you hear the sound");
        dlgAlert.setTitle("Sound test");
        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                timer();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    protected void setCountOfAttempts(){
        Random r = new Random();
        int low = 5;
        int high = 10;
        countOfAttempts = r.nextInt(high-low) + low;
    }

    protected void timer(){
        final Handler handler = new Handler();
        final TextView textView = findViewById(R.id.textView3);
        final java.util.concurrent.atomic.AtomicInteger n = new AtomicInteger(3);
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                textView.setText(Integer.toString(n.get()));
                if(n.getAndDecrement() >= 1 )
                    handler.postDelayed(this, 1000);
                else {

                    //TODO Remove showToast
                    countOfAttempts--;
                    sr.increaseLvl(1);
                    showButton();

                }
            }
        };
        handler.postDelayed(counter, 1000);
    }

    protected void showButton(){
        if(countOfAttempts == 0){
            Toast.makeText(this, "Testing ended", Toast.LENGTH_LONG).show();
            return;
        }
        mpplay = false;
        yes.setVisibility(View.VISIBLE);
        no.setVisibility(View.VISIBLE);
        Random r = new Random();
        int songPos = r.nextInt(songs.length);
        if(songPos == 0) {
            mp = MediaPlayer.create(this, R.raw.m0);
            mp.start();
            mpplay = true;
        } else if(songPos == 1){
            mp = MediaPlayer.create(this, R.raw.m1);
            mp.start();
            mpplay = true;
        }else if(songPos == 2){
            sr.increaseLvl(1);
        } else if(songPos == 3){
            mp = MediaPlayer.create(this, R.raw.m2);
            mp.start();
            mpplay = true;
        } else if(songPos == 4){
            mp = MediaPlayer.create(this, R.raw.m3);
            mp.start();
            mpplay = true;
        } else if(songPos == 5){
            sr.increaseLvl(1);
        } else if(songPos == 6){
            mp = MediaPlayer.create(this, R.raw.m4);
            mp.start();
            mpplay = true;
        } else if(songPos == 7){
            mp = MediaPlayer.create(this, R.raw.m5);
            mp.start();
            mpplay = true;
        } else if(songPos == 8){
            sr.increaseLvl(1);
        } else if(songPos == 9){
            mp = MediaPlayer.create(this, R.raw.m6);
            mp.start();
            mpplay = true;
        } else if(songPos == 10){
            mp = MediaPlayer.create(this, R.raw.m7);
            mp.start();
            mpplay = true;
        } else if(songPos == 11){
            sr.increaseLvl(1);
        }
        else sr.increaseLvl(1);

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                    if (mpplay) {
                        mp.stop();
                    }
                    hideButton();
                    timer();
                    countOfAttempts--;

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mpplay) {
                    mp.stop();
                }
                hideButton();
                timer();
                countOfAttempts--;
            }
        });
    }

    protected void hideButton(){
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
    }

    private void setAlarm(long timeInMillis, Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(SoundTest.this, SoundTestAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SoundTest.this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    @Override
    public void onBackPressed() {
        Calendar calendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= 23) {
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE)+1,//+1 min posun
                    0
            );
        } else {
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE)+1,//+1 min posun
                    0
            );
        }

            setAlarm(100000, calendar);
            sr.increaseLvl(5);
            finish();
    }
}
