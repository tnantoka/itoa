package com.bornneet.capicondemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import com.bornneet.capicon.Capicon;

public class MainActivity extends AppCompatActivity {

    ImageView imageCapicon;
    EditText editString;
    SeekBar seekScale;
    TextView textScale;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageCapicon = (ImageView)findViewById(R.id.image_capicon);
        editString = (EditText)findViewById(R.id.edit_string);
        seekScale = (SeekBar)findViewById(R.id.seek_scale);
        textScale = (TextView)findViewById(R.id.text_scale);
        Button buttonExport = (Button)findViewById(R.id.button_export);

        editString.setText("C");
        editString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateCapicon();
            }
        });

        seekScale.setProgress(70);
        seekScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateCapicon();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export();
            }
        });

        updateCapicon();
    }

    private void updateCapicon() {
        float scale = (seekScale.getProgress() + 10) / 100.0f;
        textScale.setText(String.format("%.2f", scale));

        Capicon capicon = new Capicon(300);
        capicon.backgroundColor = Capicon.Colors.PINK;
        capicon.string = editString.getText().toString();
        capicon.textScale = scale;

        bitmap = capicon.bitmap();
        imageCapicon.setImageBitmap(bitmap);
    }

    private void export() {
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
