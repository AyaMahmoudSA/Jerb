package com.av.jerb;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.av.jerb.Data.City;
import com.av.jerb.Data.DatabaseHandler;
import com.av.jerb.Data.StoreData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private TextView textDays,textHours,textMinutes,textSeconds,textAddCover;
    private RelativeLayout showDatePicker;
    private ImageView changeCoverPhoto;
    private FloatingActionButton floatingActionButton;

    public static Context context;
    // Timer setup
    public  Time conferenceTime = new Time(Time.getCurrentTimezone());
    CountDownTimer countDownTimer=null;
    // Values displayed by the timer
    private int mDisplayDays;
    private int mDisplayHours;
    private int mDisplayMinutes;
    private int mDisplaySeconds;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final int GALLERY_PICK=1;
    DatabaseHandler db = new DatabaseHandler(MainActivity.this);

    // URL of object to be parsed
     String URL_COUNTRY_CITY= "https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();


        /***************************************************************************
         * Wedding Date
         * First part add date and start it count down
         ****************************************************************************/
        textDays = (TextView) findViewById(R.id.txt_days);
        textHours = (TextView) findViewById(R.id.txt_hours);
        textMinutes = (TextView) findViewById(R.id.txt_minutes);
        textSeconds = (TextView) findViewById(R.id.txt_seconds);
        showDatePicker = (RelativeLayout) findViewById(R.id.count_down);

        showDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // To set Calender with current date
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year2, int month2, int day) {
                month2 = month2 +1;
                int  monthDay=day;
                int  month=month2-1;
                int  year=year2;
                Calendar  c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int  minute=c.get(Calendar.MINUTE);
                int second=c.get(Calendar.SECOND);

                new StoreData().saveSeconds(second);
                new StoreData().saveMinutes(minute);
                new StoreData().saveHours(hour);
                new StoreData().saveMonthDay(monthDay);
                new StoreData().saveMonth(month);
                new StoreData().saveYear(year);


                if(countDownTimer!=null){
                    countDownTimer.cancel();
                    configureConferenceDate(second,minute,hour,monthDay,month,year);

                }else{
                    configureConferenceDate(second,minute,hour,monthDay,month,year);

                }
            }
        };
        // When launch app again start it from stop
        // get it from shared preference
        int getSeconds = new StoreData().loadSeconds();
        int getMinutes = new StoreData().loadMinutes();
        final int getHours = new StoreData().loadHours();
        int getMonthDay = new StoreData().loadMonthDay();
        int getMonth = new StoreData().loadMonth();
        int getYear = new StoreData().loadYear();
        if(getYear!=0 || getSeconds!=0 || getHours!=0 || getMinutes !=0 ||getMonth!=0 ){

            configureConferenceDate(getSeconds,getMinutes,getHours,getMonthDay,getMonth,getYear);

        }
     /****************************Finish First Part Wedding Date*****************************************************/



        /*******************************************************************************************
         * Wedding Date
         * Second part add cover photo
         ******************************************************************************************/

        textAddCover = (TextView) findViewById(R.id.add_cover);
        changeCoverPhoto =(ImageView)findViewById(R.id.img_coverPhoto);

        textAddCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_PICK);
            }
        });

        // to get save photo when app launch again
       String getImageLoad = new StoreData().loadImage();
        if( !getImageLoad.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(getImageLoad, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            changeCoverPhoto.setImageBitmap(bitmap);
        }

        /****************************Finish Second Part Wedding Date*****************************************************/

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent planActivity = new Intent(MainActivity.this,PlanActivity.class);
                startActivity(planActivity);
            }
        });

        List<City> cityList = db.getAllCity();
        if(cityList.size()!=0){
          //do nothing


        }else{
            // Creates the Volley request queue
            requestQueue = Volley.newRequestQueue(this);
            // Creating the JsonObjectRequest class called obreq, passing required parameters:
            //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_COUNTRY_CITY, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray getCityofcountry = response.getJSONArray("Egypt");
                        for(int i =0;i<=getCityofcountry.length();i++){
                            City c= new City();
                            String getcity = getCityofcountry.get(i).toString();
                            c.setCityName(getcity);
                            Log.d("Insert: ", "Inserting ..");
                            db.addCity(c);

                        }
                    } catch (JSONException e) {



                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);

        }


    }


    //Method call to start count down from current date to date that user enter it
    //input  : current time with second,minute ,hour and user date monthday , month ,year
    //result start count down every 1 sec from current date to specfic date
    private void configureConferenceDate(int second,int minute,int hour,int monthDay,int month,int year) {

        conferenceTime.set(second,minute,hour,monthDay, month, year);
        conferenceTime.normalize(true);
        long confMillis = conferenceTime.toMillis(true);

        Time nowTime = new Time(Time.getCurrentTimezone());
        nowTime.setToNow();
        nowTime.normalize(true);
        long nowMillis = nowTime.toMillis(true);

        long milliDiff = confMillis - nowMillis;

        countDownTimer=  new CountDownTimer(milliDiff, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // decompose difference into days, hours, minutes and seconds
                mDisplayDays = (int) ((millisUntilFinished / 1000) / 86400);
                mDisplayHours = (int) (((millisUntilFinished / 1000) - (mDisplayDays * 86400)) / 3600);
                mDisplayMinutes = (int) (((millisUntilFinished / 1000) - ((mDisplayDays * 86400) + (mDisplayHours * 3600))) / 60);
                mDisplaySeconds = (int) ((millisUntilFinished / 1000) % 60);


             textDays.setText(String.valueOf(mDisplayDays));
             textHours.setText(String.valueOf(mDisplayHours));
             textMinutes.setText(String.valueOf(mDisplayMinutes));
             textSeconds.setText(String.valueOf(mDisplaySeconds));
            }

            @Override
            public void onFinish() {
                //TODO: this is where you would launch a subsequent activity if you'd like.  I'm currently just setting the seconds to zero
                textDays.setText("00");
                textHours.setText("00");
                textMinutes.setText("00");
                textSeconds.setText("00");
            }
        }.start();
    }


    // Override method call when user select photo from
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                new StoreData().saveImage(encodedImage);
                changeCoverPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
        }

        }



}
