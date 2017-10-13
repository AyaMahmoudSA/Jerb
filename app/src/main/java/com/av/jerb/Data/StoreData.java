package com.av.jerb.Data;

import android.content.Context;
import android.content.SharedPreferences;

import com.av.jerb.MainActivity;

/**
 * Created by Maiada on 10/13/2017.
 */

public class StoreData {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String DATABASE_NAME = "com.av.jerb";
    private Context context;

    public StoreData() {
        super();
        this.context = MainActivity.context;
        sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSeconds(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Seconds", value);
        editor.commit();
    }
    public int loadSeconds(){
        int  savedValue = sharedPreferences.getInt("Seconds", 0);
        return savedValue;
    }

    public void saveMinutes(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Minutes", value);
        editor.commit();
    }
    public int loadMinutes(){
        int  savedValue = sharedPreferences.getInt("Minutes", 0);
        return savedValue;
    }


    public void saveHours(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Hours", value);
        editor.commit();
    }
    public int loadHours(){
        int  savedValue = sharedPreferences.getInt("Hours", 0);
        return savedValue;
    }
    public void saveMonthDay(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("MonthDay", value);
        editor.commit();
    }
    public int loadMonthDay(){
        int  savedValue = sharedPreferences.getInt("MonthDay", 0);
        return savedValue;
    }


    public void saveMonth(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Month", value);
        editor.commit();
    }
    public int loadMonth(){
        int  savedValue = sharedPreferences.getInt("Month", 0);
        return savedValue;
    }

    public void saveYear(int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Year", value);
        editor.commit();
    }
    public int loadYear(){
        int  savedValue = sharedPreferences.getInt("Year", 0);
        return savedValue;
    }
}
