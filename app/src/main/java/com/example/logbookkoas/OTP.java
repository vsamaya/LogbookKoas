package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    TextView Waktu;
    int i=0;
    private ProgressBar progressBarCircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Waktu = (TextView) findViewById(R.id.waktu);
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                progressBarCircle.setProgress((int)i*100/(300000/1000));

                String waktu = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                Waktu.setText(waktu);
            }

            public void onFinish() {
                finish();

            }
        }.start();

        Random rand=new Random();
        int number = rand.nextInt(8999)+1000;
        TextView myText = (TextView)findViewById(R.id.generatenumber);
        String otp = String.valueOf(number);
        myText.setText(otp);


    }



}
