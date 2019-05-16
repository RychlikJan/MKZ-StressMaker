package com.example.stressmaker;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Random;

public class RejectActivity extends AppCompatActivity {
public String[] noWords = {"No","Never","Die","Go away", "Fucked up","Fuck you","Haw Haw","Im not gonna end","Im not your servant"
        ,"Let me be","D'oh"};
   private int countOfAttempts;
   StressLevel  sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Rejecting Mode");
        setContentView(R.layout.activity_reject);
        setAttempts();
        sr = StressLevel.getInstance();
    }
    protected void setAttempts(){
        Random r = new Random();
        int low = 3;
        int high = 6;
        countOfAttempts = r.nextInt(high-low) + low;
    }

    /**
     * Mehot to override backpress
     */
    @Override
    public void onBackPressed() {
        if(countOfAttempts == 0){
            finish();
        }else {

            Random r = new Random();
            int pos = r.nextInt(noWords.length);
            if(pos ==6){
                MediaPlayer mp = MediaPlayer.create(this,R.raw.the_simpsons_nelsons_haha);
                mp.start();
            }
            Toast.makeText(this, noWords[pos] , Toast.LENGTH_SHORT).show();
            sr.increaseLvl(1);
            countOfAttempts--;
        }
    }
}
