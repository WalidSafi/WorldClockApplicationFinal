package com.example.worldclockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        Intent intent = getIntent();

        System.out.println("man");

    }

    public void goBack(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}