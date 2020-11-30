package com.example.worldclockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

    }

    public void goBack(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startTimer(View v) {

        timeView = (TextView) findViewById(R.id.txtShowTime);
        inpTime = (EditText) findViewById(R.id.editMinutes);
        can = (Button) findViewById(R.id.btnCancelTimer);
        String timeHolder = inpTime.getText().toString();

        if (timeHolder.equals("")) {
            Toast.makeText(this, "Please enter a Valid Minute", Toast.LENGTH_LONG).show();
        } else {

            long time = Long.parseLong(timeHolder);
            time = time * 60000;

            CountDownTimer timer = new CountDownTimer(time, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    timeView.setText("Time:  " + millisUntilFinished / 1000);
                    timeView.setTextColor(getResources().getColor(R.color.black));

                }

                @Override
                public void onFinish() {
                    timeView.setText("Timer is completed");
                    timeView.setTextColor(getResources().getColor(R.color.AddBlue));
                }
            }.start();

            can.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeView.setText("Cancelled");
                    timeView.setTextColor(getResources().getColor(R.color.ResetRed));
                    timer.cancel();
                }
            });
        }
    }
}