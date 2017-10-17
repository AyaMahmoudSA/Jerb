package com.av.jerb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
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
import com.av.jerb.Data.StoreData;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.adw.library.widgets.discreteseekbar.internal.Marker;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;

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
    private  boolean checkedBudget;
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
        checkedBudget = checkBoxBudget.isChecked();
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

                boolean checkedLocation = checkBoxLocation.isChecked();
                if(checkedLocation){


                    boolean checkedBudget = checkBoxBudget.isChecked();
                    if(checkedBudget){
                       /* Toast.makeText(PlanActivity.this, "put not decide yet", Toast.LENGTH_SHORT).show();
                        Toast.makeText(PlanActivity.this, "add Any db", Toast.LENGTH_SHORT).show();
                        Toast.makeText(PlanActivity.this, ""+discreteSeekBar.getProgress(), Toast.LENGTH_SHORT).show();*/
                        if(databaseHandlerPlan.getAllPlans().size()!=0){
                            String getLastAlphabet =  new StoreData().loadAlphabet();
                            int charValue = getLastAlphabet .charAt(0);
                            String nextAlphabet = String.valueOf( (char) (charValue + 1));
                            new StoreData().saveAlphabet(nextAlphabet);
                            plans.setPlanName(nextAlphabet);
                            plans.setLocation("Any");
                            plans.setMemberNumber(""+discreteSeekBar.getProgress());
                            plans.setBudget("Not Decide");
                            databaseHandlerPlan.addPlan(plans);


                        }else{
                            String firstAlphabet = "A";
                            new StoreData().saveAlphabet(firstAlphabet);
                            plans.setPlanName(firstAlphabet);
                            plans.setLocation("Any");
                            plans.setMemberNumber(""+discreteSeekBar.getProgress());
                            plans.setBudget("Not Decide");
                            databaseHandlerPlan.addPlan(plans);

                        }


                        Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                        startActivity(intent);

                    }else{
                        if(moneyRange.getText().length()>0){
                            if(Long.valueOf(moneyRange.getText().toString())<10000||Long.valueOf(moneyRange.getText().toString())>100000000){

                                Toast.makeText(PlanActivity.this, moneyRange.getText().toString(), Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            Toast.makeText(PlanActivity.this, "also you must enter budget", Toast.LENGTH_SHORT).show();


                        }

                    }
                }else{

                    if(autoCompleteTextView.getText().length()>0){

                        boolean checkedBudget = checkBoxBudget.isChecked();
                        if(checkedBudget){

                           /* Toast.makeText(PlanActivity.this, "put not decide yet", Toast.LENGTH_SHORT).show();
                            Toast.makeText(PlanActivity.this, "add city db", Toast.LENGTH_SHORT).show();
                            Toast.makeText(PlanActivity.this, ""+discreteSeekBar.getProgress(), Toast.LENGTH_SHORT).show();*/

                            if(databaseHandlerPlan.getAllPlans().size()!=0){
                                String getLastAlphabet =  new StoreData().loadAlphabet();
                                int charValue = getLastAlphabet .charAt(0);
                                String nextAlphabet = String.valueOf( (char) (charValue + 1));
                                new StoreData().saveAlphabet(nextAlphabet);
                                plans.setPlanName(nextAlphabet);
                                plans.setLocation(autoCompleteTextView.getText().toString());
                                plans.setMemberNumber(""+discreteSeekBar.getProgress());
                                plans.setBudget("Not Decide");
                                databaseHandlerPlan.addPlan(plans);

                                Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                                startActivity(intent);

                            }else{
                                String firstAlphabet = "A";
                                new StoreData().saveAlphabet(firstAlphabet);
                                plans.setPlanName(firstAlphabet);
                                plans.setLocation(autoCompleteTextView.getText().toString());
                                plans.setMemberNumber(""+discreteSeekBar.getProgress());
                                plans.setBudget("Not Decide");
                                databaseHandlerPlan.addPlan(plans);

                                Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                                startActivity(intent);

                            }





                        }else{
                            if(moneyRange.getText().length()>0){
                                if(moneyRange.getText().length()<10000||moneyRange.getText().length()>100000000){

  /*                                  Toast.makeText(PlanActivity.this, moneyRange.getText().toString(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(PlanActivity.this, "add city db", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(PlanActivity.this, ""+discreteSeekBar.getProgress(), Toast.LENGTH_SHORT).show();*/





                                    if(databaseHandlerPlan.getAllPlans().size()!=0){
                                        String getLastAlphabet =  new StoreData().loadAlphabet();
                                        int charValue = getLastAlphabet .charAt(0);
                                        String nextAlphabet = String.valueOf( (char) (charValue + 1));
                                        new StoreData().saveAlphabet(nextAlphabet);
                                        plans.setPlanName(nextAlphabet);
                                        plans.setLocation(autoCompleteTextView.getText().toString());
                                        plans.setMemberNumber(""+discreteSeekBar.getProgress());
                                        plans.setBudget(moneyRange.getText().toString());
                                        databaseHandlerPlan.addPlan(plans);

                                        Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    }else{
                                        String firstAlphabet = "A";
                                        new StoreData().saveAlphabet(firstAlphabet);
                                        plans.setPlanName(firstAlphabet);
                                        plans.setLocation(autoCompleteTextView.getText().toString());
                                        plans.setMemberNumber(""+discreteSeekBar.getProgress());
                                        plans.setBudget(moneyRange.getText().toString());
                                        databaseHandlerPlan.addPlan(plans);

                                        Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    }



                                }

                            }else{
                                Toast.makeText(PlanActivity.this, "also you must enter budget", Toast.LENGTH_SHORT).show();

                            }

                        }


                    } else{
                        Toast.makeText(PlanActivity.this, "you must enter city", Toast.LENGTH_SHORT).show();

                    }





                }

            }
        });







    }
}
