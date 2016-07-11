package com.bornneet.helloworld;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TimeLayout timeJST;
    TimeLayout timePST;
    TimeLayout timeUTC;

    int hours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeJST = (TimeLayout)findViewById(R.id.time_jst);
        timePST = (TimeLayout)findViewById(R.id.time_pst);
        timeUTC = (TimeLayout)findViewById(R.id.time_utc);

        updateTimes();

        final Handler handler = new Handler();
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateTimes();
                    }
                });
            }
        }, 0, 1000 * 60);

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Button buttonPrev = (Button)findViewById(R.id.button_prev);
        Button buttonNext = (Button)findViewById(R.id.button_next);
        Button buttonReset = (Button)findViewById(R.id.button_reset);

        buttonPrev.setTypeface(fontAwesome);
        buttonNext.setTypeface(fontAwesome);
        buttonReset.setTypeface(fontAwesome);

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHours(hours - 1);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHours(hours + 1);
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHours(0);
            }
        });
    }

    private void updateTimes() {
        updateTime(timeJST, "Asia/Tokyo");
        updateTime(timePST, "America/Los_Angeles");
        updateTime(timeUTC, "UTC");
    }

    private void updateTime(TimeLayout time, String timeZoneId) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        Calendar calendar = Calendar.getInstance(timeZone);

        calendar.add(Calendar.HOUR, hours);

        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());

        dateFormat.setTimeZone(timeZone);
        timeFormat.setTimeZone(timeZone);

        String day = (String)android.text.format.DateFormat.format("E", calendar);

        String text = dateFormat.format(calendar.getTime()) + " " + timeFormat.format(calendar.getTime()) + " (" + day + ")";
        time.timeText.setText(text);

        time.zoneText.setText(timeZone.getDisplayName());
        time.offsetText.setText(android.text.format.DateFormat.format("z", calendar));
    }

    private void updateHours(int hours) {
        this.hours = hours;
        updateTimes();
    }
}
