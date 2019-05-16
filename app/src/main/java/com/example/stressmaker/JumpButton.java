package com.example.stressmaker;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class JumpButton extends AppCompatActivity {
    StressLevel sr;
    Button btnJmp;
    int countClick;
    int timerSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_button);
        sr = StressLevel.getInstance();
        btnJmp = findViewById(R.id.btnJmp);
        countClick = 0;
        timerSpeed = 1000;
        timer();

    }
    protected void timer(){
        final Handler handler = new Handler();
        final java.util.concurrent.atomic.AtomicInteger n = new AtomicInteger(2);
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                if(n.getAndDecrement() >= 1 )
                    handler.postDelayed(this, timerSpeed);
                else {

                    //TODO Remove showToast
                    sr.increaseLvl(1);
                    jmpButton();
                    timer();

                }
            }
        };
        handler.postDelayed(counter, timerSpeed);
    }

    protected void jmpButton(){
        btnJmp.setVisibility(View.VISIBLE);
        AbsoluteLayout.LayoutParams absParams =
                (AbsoluteLayout.LayoutParams)btnJmp.getLayoutParams();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels -255;
        int height = displaymetrics.heightPixels-250;
        if(width < 250){
            width = 250;
        }
        if(height < 250){
            height = 250;
        }
        Random r = new Random();
        absParams.x =  r.nextInt(width);
        absParams.y =  r.nextInt(height);
        btnJmp.setLayoutParams(absParams);
        if(r.nextInt(6)%4 == 0){
            btnJmp.setVisibility(View.INVISIBLE);
        }

    }

    public  void btnJumpClick(){
        countClick++;
        if(countClick == 3){
            finish();
        }
        else{
            timerSpeed = (int)timerSpeed/2;
        }

    }
}
