package com.bornneet.generativepolygon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    TextView textCircuits;
    PolygonView polygonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polygonView = (PolygonView)findViewById(R.id.view_polygon);
        textCircuits = (TextView)findViewById(R.id.text_circuits);

        SeekBar seekCircuits = (SeekBar)findViewById(R.id.seek_circuits);
        seekCircuits.setMax(5);
        seekCircuits.setProgress(3);
        seekCircuits.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                polygonView.circuits = i + 1;
                updatePolygonView();
                updateTextCircuits();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        updateTextCircuits();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updatePolygonView();
            return true;
        }

        if (id == R.id.action_export) {
            exportPolygonView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateTextCircuits() {
        textCircuits.setText(String.valueOf(polygonView.circuits));
    }

    private void updatePolygonView() {
        polygonView.invalidate();
    }

    private void exportPolygonView() {
        Bitmap bitmap = polygonView.bitmap;

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
    }
}
