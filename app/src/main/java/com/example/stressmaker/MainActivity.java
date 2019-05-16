package com.example.stressmaker;

//https://developer.android.com/training/system-ui/navigation



import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    StressLevel sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         sr = StressLevel.getInstance();
         sr.setLvl(0);
         if(sr.getLvl() == 0 ){
             showStartDialog();
         }
        setContentView(R.layout.activity_main);
    }



    public void fastClick(View v){
        Intent i = new Intent(this,FastClickActivity.class);
        startActivity(i);
    }

    public void rejectClick(View v){
        Intent i = new Intent(this,RejectActivity.class);
        startActivity(i);
    }

    public void alarmClick(View v){
        Intent i = new Intent(this,Alarm2.class);
        startActivity(i);
    }
    public void soundTestClick(View v){
        Intent i = new Intent(this,SoundTest.class);
        startActivity(i);
    }
    public void jumpButtonClick(View v){
        Intent i = new Intent(this, JumpButton.class);
        startActivity(i);
    }

    public void showStartDialog(){
        final String[] m_Text = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set your actual stress level");
        builder.setMessage("Input will be convert to percent.");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText() == null){
                    input.setText("0");
                }
                m_Text[0] = input.getText().toString();
                int result = Integer.parseInt(m_Text[0]);
                result +=1;
                sr.setLvl(result%100);

            }
        });
        builder.show();

      /*  int result = Integer.parseInt(m_Text[0]);
        result +=1;
        sr.setLvl(result%100);*/
    }
}