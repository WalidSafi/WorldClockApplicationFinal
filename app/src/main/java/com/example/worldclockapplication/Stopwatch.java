package com.example.worldclockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.os.Vibrator;

public class Stopwatch extends AppCompatActivity {

    Chronometer watch;
    long pausedtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        Intent intent = getIntent();
        System.out.println("Hello StopWatch");

        watch = (Chronometer) findViewById(R.id.chronWatch);
        watch.setBase(SystemClock.elapsedRealtime());

        Vibrator testing = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        watch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long t = SystemClock.elapsedRealtime() - chronometer.getBase();

                if(t >= 10000) {
                    System.out.println("Ten seconds has passed");
                } else {
                    System.out.println("Not working");
                }
            }
        });

    }

    public void goBack(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void startWatch(View v){
        watch.setBase(SystemClock.elapsedRealtime() - pausedtime);
        watch.start();

    }

    public void stopWatch(View v){
        watch.stop();
        pausedtime = SystemClock.elapsedRealtime() - watch.getBase();
    }

    public void resetWatch(View v){
        watch.stop();
        watch.setBase(SystemClock.elapsedRealtime());
        pausedtime = 0;
    }
}