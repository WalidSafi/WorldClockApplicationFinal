package com.example.worldclockapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "WorldClocks.db";
    private static final String TABLE_NAME = "Clocks";
    private static final String COL_1 = "id";
    private static final String COL_2 = "regions";
    private static final String COL_3 = "cityname";
    private static final String COL_4 = "timezone";
    private static final String COL_5 = "localtime";
    private static final String COL_6 = "utctime";

    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " Text NOT NULL," +
                COL_3 + " Text NOT NULL," +
                COL_4 + " Text NOT NULL," +
                COL_5 + " Text NOT NULL," +
                COL_6 + " Text NOT NULL)" + ";" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String region, String city, String abbreviation, String local, String utc){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, region);
        contentValues.put(COL_3, city);
        contentValues.put(COL_4, abbreviation);
        contentValues.put(COL_5, local);
        contentValues.put(COL_6, utc);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public ArrayList<String> getClockRecords(){

        ArrayList<String> clockInfo = new ArrayList<String>();

        String name;
        String zone;
        String time;
        String utctime;

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");


        SQLiteDatabase db = getReadableDatabase();
        String query = "select " + COL_3 + ", " + COL_4 + ", " + COL_6 + " from " + TABLE_NAME + ";" ;


        Log.d("query",query);
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){

            name = cursor.getString(0);
            System.out.println("City name is: " + name);
            zone = cursor.getString(1);
            utctime = cursor.getString(2);


            TimeZone tz = TimeZone.getTimeZone(name);
            sf.setTimeZone(tz);
            time = sf.format(date);

            System.out.println(utctime);
            utctime = new String(utctime.substring(11, 19));

            String currentValue = name + "  " + zone + "  \n" + "Local Time: " + time;
            clockInfo.add(currentValue);

        }

        cursor.close();
        return  clockInfo;

    }

    public void clearDatebase(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

    }
    public void deleteClock(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(cityName);
        db.delete(TABLE_NAME, COL_3 + "=?", new String[]{cityName});
    }

    public ArrayList<String> getEditClockRecords(){

        ArrayList<String> clockInfo = new ArrayList<String>();

        String name;

        SQLiteDatabase db = getReadableDatabase();
        String query = "select "  + COL_3 + " from " + TABLE_NAME + ";" ;

        Log.d("query",query);
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){

            name = cursor.getString(0);
            System.out.println("Edit Clock City name is: " + name);
            clockInfo.add(name);

        }

        cursor.close();
        return clockInfo;
    }

}
