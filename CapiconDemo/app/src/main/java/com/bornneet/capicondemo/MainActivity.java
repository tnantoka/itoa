package com.bornneet.capicondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bornneet.capicon.Capicon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Capicon capicon = new Capicon(300, 300);

        ImageView imageCapicon = (ImageView)findViewById(R.id.image_capicon);
        imageCapicon.setImageBitmap(capicon.bitmap());
    }
}
