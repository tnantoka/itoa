package com.bornneet.fetchcurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textRate = (TextView)findViewById(R.id.text_rate);
        TextView textUpdated = (TextView)findViewById(R.id.text_updated);

        textRate.setText("");
        textUpdated.setText("");
    }
}
