package com.example.worldclockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditClock extends AppCompatActivity {

    ListView list;
    MySQLiteHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clock);

        Intent intent = getIntent();
        getRecords();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String clicked = (String) parent.getItemAtPosition(position);
                Toast.makeText(EditClock.this, "You Selected: " + clicked  , Toast.LENGTH_SHORT).show();
                mydb.deleteClock(clicked);
                getRecords();

            }
        });

    }

    public void goBack(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public  void getRecords(){

        mydb = new MySQLiteHelper(this,null,null,1);

        list = (ListView) findViewById(R.id.editList);

        ArrayList<String> editArray = new ArrayList<>();
        editArray = mydb.getEditClockRecords();

        ArrayAdapter arrayAdapter = new ArrayAdapter(EditClock.this, android.R.layout.simple_list_item_1, editArray);
        list.setAdapter(arrayAdapter);

    }
}