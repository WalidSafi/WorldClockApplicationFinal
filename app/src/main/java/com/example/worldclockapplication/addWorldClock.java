package com.example.worldclockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class addWorldClock extends AppCompatActivity {

    Spinner regions;
    String region;
    String CityName;
    EditText edtCityName;
    MySQLiteHelper mydb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_world_clock);
        mydb = new MySQLiteHelper(this,null,null,1);
        //mydb.insertData("America", "Toronto", "EST", "18:08:43", "23:07:43"); Testing if adding would work

        Intent intent = getIntent();
        regions = (Spinner) findViewById(R.id.regions_spinner);
        edtCityName = findViewById(R.id.edtName);

        setSpinner();
        System.out.println("WorldClock");

    }

    public void goBack(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void callAPI(View v){

        if (edtCityName.getText().toString().equals("")){

            Toast.makeText(addWorldClock.this, "Please enter a City Name" , Toast.LENGTH_SHORT).show();
            return;
        }
        JsonCall getData = new JsonCall();
        getData.execute();

    }

    public void setSpinner(){

        String[] regionList = getResources().getStringArray(R.array.regions_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, regionList);
        regions.setAdapter(adapter);
    }

    public void reset(View v){

        edtCityName.setText(" ");

    }

    public class JsonCall extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            try {

                CityName = edtCityName.getText().toString();
                region = regions.getSelectedItem().toString();


                URL url = new URL("http://worldtimeapi.org/api/timezone/"+region+"/"+CityName);
                System.out.println(url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputstream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

                StringBuffer buffer = new StringBuffer();
                String line= "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String jsonString = result;

            try {

                JSONObject jsonObject = new JSONObject(jsonString);

                String insertCity = jsonObject.getString("timezone");
                String insertZone = jsonObject.getString("abbreviation");
                String insertLocal = jsonObject.getString("datetime");
                String insertUTC = jsonObject.getString("utc_datetime");

                mydb.insertData(region, insertCity, insertZone, insertLocal, insertUTC);

                System.out.println(insertZone);


                Toast.makeText(addWorldClock.this, CityName + " Has been added to the Database", Toast.LENGTH_SHORT).show();
                edtCityName.setText("");

            } catch(JSONException e) {
                e.printStackTrace();
            }

        }
    }
}