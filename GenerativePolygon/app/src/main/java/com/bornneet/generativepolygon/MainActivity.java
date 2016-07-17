package com.bornneet.generativepolygon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

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
                polygonView.invalidate();
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

    private void updateTextCircuits() {
        textCircuits.setText(String.valueOf(polygonView.circuits));
    }
}
