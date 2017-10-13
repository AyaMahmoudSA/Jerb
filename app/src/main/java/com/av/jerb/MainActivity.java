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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.av.jerb.Data.StoreData;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private TextView textDays,textHours,textMinutes,textSeconds,textAddCover;
    private RelativeLayout showDatePicker;
    ImageView changeCoverPhoto;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        this.conferenceTime.setToNow();

        textDays = (TextView) findViewById(R.id.txt_days);
        textHours = (TextView) findViewById(R.id.txt_hours);
        textMinutes = (TextView) findViewById(R.id.txt_minutes);
        textSeconds = (TextView) findViewById(R.id.txt_seconds);
        showDatePicker = (RelativeLayout) findViewById(R.id.count_down);
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




        int getSeconds = new StoreData().loadSeconds();
        int getMinutes = new StoreData().loadMinutes();
        int getHours = new StoreData().loadHours();
        int getMonthDay = new StoreData().loadMonthDay();
        int getMonth = new StoreData().loadMonth();
        int getYear = new StoreData().loadYear();

        if(getYear!=0 || getSeconds!=0 || getHours!=0 || getMinutes !=0 ||getMonth!=0 ){

            configureConferenceDate(getSeconds,getMinutes,getHours,getMonthDay,getMonth,getYear);

        }

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
                       R.style.DialogStyle,
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
              //  Log.d("TAG", "onDateSet: mm/dd/yyy: " + month2 + "/" + day + "/" + year);
                int  monthDay=day;
                int  month=month2-1;
                  int  year=year2;
                Calendar  c = Calendar.getInstance();
               int hour = c.get(Calendar.HOUR)+12;
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
             //   startService(new Intent(MainActivity.this, CountDownTimerService.class));
              //  Log.i(TAG, "Started service");
              //  String date = month + "/" + day + "/" + year;
            //    mDisplayDate.setText(date);
            }
        };






    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                changeCoverPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            //    Toast.makeText(PostImage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
         //   Toast.makeText(PostImage.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
            //     CropImage.activity(ImageUri).setAspectRatio(1,1).start(this);
            // circleImageView .setImageResource(ImageUri);

        }


}
