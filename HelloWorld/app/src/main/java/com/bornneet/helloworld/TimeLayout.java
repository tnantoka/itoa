package com.bornneet.helloworld;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tnantoka on 7/10/16.
 */
public class TimeLayout extends LinearLayout {
    TextView zoneText;
    TextView offsetText;
    TextView timeText;

    public TimeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_time, this);

        zoneText = (TextView)findViewById(R.id.zone_text);
        offsetText = (TextView)findViewById(R.id.offset_text);
        timeText = (TextView)findViewById(R.id.time_text);
    }
}
