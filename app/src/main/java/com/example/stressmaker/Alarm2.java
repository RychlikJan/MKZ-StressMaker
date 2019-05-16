package com.example.stressmaker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

public class Alarm2 extends AppCompatActivity {



    TimePicker tp_time;
    TextView tv_display;
    Button btn_set, btn_reset;
    StressLevel sr;
    int timeMove;

    /**
     * Method on  create.
     * Init the layouts and set on click methods
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
        sr = StressLevel.getInstance();
        timeMove = setTimeMove();
        tv_display = (TextView) findViewById(R.id.tv_display);
        tp_time = (TimePicker) findViewById(R.id.tp_time);
        btn_set = (Button) findViewById(R.id.btn_set);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_set.setOnClickListener(new View.OnClickListener() {
            /**
             * Method on click at alarm
             * @param v
             */
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (Build.VERSION.SDK_INT >= 23) {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            tp_time.getHour(),
                            tp_time.getMinute() + timeMove,
                            0
                    );
                } else {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            tp_time.getCurrentHour(),
                            tp_time.getCurrentMinute() + timeMove,
                            0
                    );
                }
                setAlarm(calendar.getTimeInMillis(), calendar);
            }

            /**
             * Method to set alarm
             * @param timeInMillis time in miliseconds from now
             * @param c calendar
             */
            private void setAlarm(long timeInMillis, Calendar c) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(Alarm2.this, AlarmAdapter.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Alarm2.this, 0, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE)-timeMove;
                int ampm = c.get(Calendar.AM_PM);
                String day = "";
                if (ampm == Calendar.AM){
                    day = "AM";
                } else if (ampm == Calendar.PM) {
                    day = "PM";
                }
                String timeText = "Alarm set for: ";
                timeText += hour + ": " + minute + " " + day;
                tv_display.setText(timeText);
                sr.increaseLvl(timeMove);
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(Alarm2.this, AlarmAdapter.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Alarm2.this, 0, intent, 0);
                alarmManager.cancel(pendingIntent);
                tv_display.setText("Alarm not set");
            }
        });
    }

    /**
     * Options menu
     * @param menu menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuexample, menu);
        return true;
    }

    /**
     * Method to certain click in menu
     * @param item item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                fstDialog();
                return true;
            case R.id.item2:
                pathMethods();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    sr.increaseLvl(1);
                    sndDialog();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    sr.increaseLvl(3);
                    break;
            }
        }
    };
    public void sndDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop Alarm");
        builder.setMessage("Do you realy want to con. with alarming?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sr.increaseLvl(3);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AudioPlay.stopAudio();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fstDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to stop alarm?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    /**
     * Method to set number of minits for length
     * @return
     */
    protected int setTimeMove(){
        Random r = new Random();
        int low = 5;
        int high = 10;
        return r.nextInt(high-low) + low;
        //return 0;
    }

    /**
     * Method to choose file.
     */
    public void pathMethods(){
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Set your required file type
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "DEMO"),1001);

    }

    /**
     * Method to work with choosed file
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            Uri currFileURI = data.getData();
            String path=currFileURI.getPath();
            if(path.contains("audio/media")) {
                recursiveDialog();

            }
            else{
                Toast.makeText(this, "Please choose from music", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Recursive dialog for choosen mp3
     */
    protected void recursiveDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want choose this song");
        builder.setMessage("Want?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recursiveDialog();
                sr.increaseLvl(1);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}