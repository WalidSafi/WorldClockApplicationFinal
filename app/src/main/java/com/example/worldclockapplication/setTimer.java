package com.example.worldclockapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class setTimer extends AppCompatActivity {

    TextView timeView;
    EditText inpTime;
    Button can;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        Intent intent = getIntent();

        timeView = (TextView) findViewById(R.id.txtShowTime);
        inpTime = (EditText) findViewById(R.id.editMinutes);
        can = (Button) findViewById(R.id.btnCancelTimer);


    }

    public void goBack(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startTimer(View v) {


        String timeHolder = inpTime.getText().toString();

        if (timeHolder.equals("")) {
            Toast.makeText(this, "Please enter a Valid Minute", Toast.LENGTH_LONG).show();
        } else {

            long time = Long.parseLong(timeHolder);
            time = time * 60000;

            CountDownTimer timer = new CountDownTimer(time, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    timeView.setText("Time:  " + millisUntilFinished / 1000 + "s");
                    timeView.setTextColor(getResources().getColor(R.color.black));

                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFinish() {
                    timeView.setText("Completed");
                    timeView.setTextColor(getResources().getColor(R.color.AddBlue));
                    timeView.setGravity(Gravity.CENTER_HORIZONTAL);
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(VibrationEffect.createOneShot(700, VibrationEffect.DEFAULT_AMPLITUDE));
                    cancel();
                }
            }.start();

            can.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeView.setText("00");
                    timeView.setTextColor(getResources().getColor(R.color.ResetRed));
                    timer.cancel();
                    Toast.makeText(setTimer.this, "Cancelled", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}