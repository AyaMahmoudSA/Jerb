package com.av.jerb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.av.jerb.Data.City;
import com.av.jerb.Data.DatabaseHandler;
import com.av.jerb.Data.DatabaseHandlerPlan;
import com.av.jerb.Data.Plans;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private DatabaseHandler db = new DatabaseHandler(PlanActivity.this);
    private List<String> citystringList= new ArrayList<>();
    private DiscreteSeekBar  discreteSeekBar;
    private Toolbar toolbar;
    private EditText moneyRange;
    private TextView nextToAdd;
    private CheckBox checkBoxLocation,checkBoxBudget;
    private DatabaseHandlerPlan databaseHandlerPlan = new DatabaseHandlerPlan(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //Handle toolbar for both back and next buttton
        toolbar = (Toolbar) findViewById(R.id.toolbar_plan);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // to back
        getSupportActionBar().setDisplayShowTitleEnabled(false); // remove label name (title)

        checkBoxLocation =(CheckBox)findViewById(R.id.checkbox_anylocation);
        checkBoxBudget =(CheckBox)findViewById(R.id.checkbox_notdecided);
        nextToAdd =(TextView) findViewById(R.id.txt_next);



        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.edittext_autocomplete_location);
        List<City> getAllListOfCity = db.getAllCity();
        for (City cn : getAllListOfCity) {
            citystringList.add(cn.getCityName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,citystringList);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });


        discreteSeekBar = (DiscreteSeekBar) findViewById(R.id.seekbar_friends_family);
        discreteSeekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value+50;
            }
        });

        moneyRange = (EditText) findViewById(R.id.et_money_cash);
        moneyRange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // first check if have value and then check this value to be between 10000 and 100000000
                if(s.length()>0){
                    if(Long.valueOf(s.toString())<10000||Long.valueOf(s.toString())>100000000){
                        moneyRange.setError("You must enter range between 10000 to 100000000");

                    }
                } else{

                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        nextToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plans plans = new Plans();

                if (checkBoxLocation.isChecked()) {


                } else {

                    if (autoCompleteTextView != null) {
                        // put location on db

                    } else {
                        autoCompleteTextView.setError("You must enter locatin");
                    }
                }



              /*  if(checkBoxBudget.isChecked()){


                }else {
                    if (moneyRange != null) {
                        //put budget to db

                    } else {
                        moneyRange.setError("You must enter budget");
                    }
                }


                     discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                         @Override
                         public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                             // put value on db

                         }

                         @Override
                         public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                         }

                         @Override
                         public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

                         }
                     });




                 }
*/


            }




        });







    }
}
