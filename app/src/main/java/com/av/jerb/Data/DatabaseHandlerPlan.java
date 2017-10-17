package com.av.jerb.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maiada on 10/16/2017.
 */

public class DatabaseHandlerPlan extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PlanOfUser";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Plans";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "planName";
    private static final String KEY_Location = "location";
    private static final String KEY_Number_Of_Family_Friends = "memberNumber";
    private static final String KEY_Budget = "budget";



    public DatabaseHandlerPlan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_Location+" TEXT,"+
                KEY_Number_Of_Family_Friends+" TEXT,"+
                KEY_Budget+" TEXT"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // Getting All Plans
    public List<Plans> getAllPlans() {
        List<Plans> planList = new ArrayList<Plans>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Plans plans = new Plans();



                plans.setId(Integer.parseInt(cursor.getString(0)));
                plans.setPlanName(cursor.getString(1));
                plans.setLocation(cursor.getString(2));
                plans.setMemberNumber(cursor.getString(3));
                plans.setBudget(cursor.getString(4));

                // Adding plan to list
                planList.add(plans);
            } while (cursor.moveToNext());
        }

        // return plan list
        return planList;
    }

    // Adding new plan
    public void addPlan(Plans plans) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, plans.getPlanName()); // plan Name
        values.put(KEY_Location, plans.getLocation()); // plan location
        values.put(KEY_Number_Of_Family_Friends, plans.getMemberNumber()); // plan number
        values.put(KEY_Budget, plans.getBudget()); // plan budget

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
}
