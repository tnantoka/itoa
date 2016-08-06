package com.bornneet.dotpict;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PictureView viewPicture = (PictureView)findViewById(R.id.view_picture);
        LinearLayout layoutColors = (LinearLayout)findViewById(R.id.layout_colors);
        Switch switchGrid = (Switch)findViewById(R.id.switch_grid);

        for (int color: viewPicture.colors) {
            Button button = new Button(this);
            button.setTag(String.valueOf(color));
            button.setBackgroundColor(color);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPicture.color = Integer.valueOf(view.getTag().toString());
                }
            });

            layoutColors.addView(button);
        }

        switchGrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewPicture.grid = b;
                viewPicture.invalidate();
            }
        });

        AdView mAdView = (AdView)findViewById(R.id.view_ad);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
//        mAdView.loadAd(adRequest);
    }
}
