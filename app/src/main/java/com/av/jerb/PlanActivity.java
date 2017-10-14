package com.av.jerb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;

import com.av.jerb.Data.City;
import com.av.jerb.Data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private   AutoCompleteTextView autoCompleteTextView;
    private DatabaseHandler db = new DatabaseHandler(PlanActivity.this);
    private List<String> citystringList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.edittext_autocomplete_location);
        List<City> getAllListOfCity = db.getAllCity();
        for (City cn : getAllListOfCity) {
            citystringList.add(cn.getCityName());
        }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,citystringList);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);


    }
}
