package com.example.stressmaker;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class FastClickActivity extends AppCompatActivity {
    private int countOfAttempts;
    StressLevel sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Fast Click Mode");
        setContentView(R.layout.activity_fast_click);
        Button button = findViewById(R.id.my_button);
        button.setVisibility(View.INVISIBLE);
        setCountOfAttempts();
        showDialog();
        sr = StressLevel.getInstance();
        //timer();

    }

    protected void setCountOfAttempts(){
        Random r = new Random();
        int low = 3;
        int high = 6;
        countOfAttempts = r.nextInt(high-low) + low;
       // countOfAttempts=100;
    }

    protected void showDialog(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("For end this mode you need fast click on button.");
        dlgAlert.setTitle("Fast Click Mode");
        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                timer();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    protected void timer(){
        final Handler handler = new Handler();
        final TextView textView = findViewById(R.id.textView);
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
                        showToast();
                        showStresLvl();
                        showButton();

                }
            }
        };
        handler.postDelayed(counter, 1000);
    }
    public void onClick(View v){
        if(countOfAttempts==0){
            finish();
        }
        else {
            Button button = findViewById(R.id.my_button);
            button.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "To late, try it again", Toast.LENGTH_LONG).show();

            timer();
        }
    }

    protected void showButton(){
        Button button = (Button)findViewById(R.id.my_button);
        AbsoluteLayout.LayoutParams absParams =
                (AbsoluteLayout.LayoutParams)button.getLayoutParams();

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

        //countOfAttempts = r.nextInt(high-low) + low;
        Random r = new Random();

       // absParams.x =  r.nextInt(width-lowWid)+lowWid;
       // absParams.y =  r.nextInt(height -lowHei)+lowHei;
        absParams.x =  r.nextInt(width);
        absParams.y =  r.nextInt(height);
        button.setLayoutParams(absParams);
        button.setVisibility(View.VISIBLE);
    }

    protected void showToast(){
        Toast.makeText(this,"Count attempts = " + countOfAttempts,Toast.LENGTH_LONG).show();
    }
    protected void showStresLvl(){
        Toast.makeText(this,"StressLvl = " + sr.getLvl(),Toast.LENGTH_LONG).show();
    }

}
