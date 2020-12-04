package com.example.worldclockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    ListView list;
    MySQLiteHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new MySQLiteHelper(this,null,null,1);

        Intent intent = getIntent();

        list = (ListView) findViewById(R.id.testlist);

        ArrayList<String> clockList = new ArrayList<>();
        clockList = mydb.getClockRecords();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clockList);
        list.setAdapter(arrayAdapter);

    }

    public void getTimer(View v){

        Intent intent = new Intent(this, setTimer.class);
        startActivity(intent);

    }

    public void getStopwatch(View v){

        Intent intent = new Intent(this, Stopwatch.class);
        startActivity(intent);

    }

    public void setAlarm(View v){

        Intent intent = new Intent(this, Alarm.class);
        startActivity(intent);

    }

    public void editClock(View v){

        Intent intent = new Intent(this, EditClock.class);
        startActivity(intent);

    }

    public void addNewClock(View v){

        Intent intent = new Intent(this, addWorldClock.class);
        startActivity(intent);
    }

    public void refreshHome(View v){
        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
        startActivity(refresh);
    }
}