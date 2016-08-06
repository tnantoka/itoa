package com.bornneet.dotpict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    PictureView viewPicture;
    View viewColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPicture = (PictureView)findViewById(R.id.view_picture);
        viewColor = (View)findViewById(R.id.view_color);
        LinearLayout layoutColors = (LinearLayout)findViewById(R.id.layout_colors);
        Switch switchGrid = (Switch)findViewById(R.id.switch_grid);
        RadioGroup groupQuality = (RadioGroup)findViewById(R.id.group_quality);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        updateColor(preferences.getInt("color", viewPicture.color));
        viewPicture.setQuality(preferences.getInt("quality", viewPicture.quality));
        viewPicture.grid = preferences.getBoolean("grid", viewPicture.grid);
        switchGrid.setChecked(viewPicture.grid);

        for (int color: viewPicture.colors) {
            Button button = new Button(this);
            button.setTag(String.valueOf(color));
            button.setBackgroundColor(color);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            button.setLayoutParams(params);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateColor(Integer.valueOf(view.getTag().toString()));
                    savePreferences();
                }
            });

            layoutColors.addView(button);
        }

        int checkedId = 0;
        for (int quality: viewPicture.qualities) {
            RadioButton radio = new RadioButton(this);
            radio.setText(String.valueOf(quality));
            radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        viewPicture.setQuality(Integer.valueOf(compoundButton.getText().toString()));
                        viewPicture.invalidate();
                        savePreferences();
                    }
                }
            });
            groupQuality.addView(radio);
            if (quality == viewPicture.quality) {
                checkedId = radio.getId();
            }
        }
        groupQuality.check(checkedId);

        switchGrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewPicture.grid = b;
                viewPicture.invalidate();
                savePreferences();
            }
        });

        AdView mAdView = (AdView)findViewById(R.id.view_ad);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_export) {
            Bitmap bitmap = viewPicture.export();
            File file = new File(getExternalCacheDir(), "export.png");

            try {
                FileOutputStream stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.flush();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_clear) {
            viewPicture.clear();
            viewPicture.invalidate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("color", viewPicture.color);
        editor.putInt("quality", viewPicture.quality);
        editor.putBoolean("grid", viewPicture.grid);
        editor.commit();
    }

    private void updateColor(int color) {
        viewPicture.color = color;
        viewColor.setBackgroundColor(color);
    }
}
