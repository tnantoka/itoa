package com.bornneet.editcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tnantoka on 7/30/16.
 */
public class NewActivity extends AppCompatActivity {

    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        editName = (EditText)findViewById(R.id.edit_name);

        Button buttonCreate = (Button)findViewById(R.id.button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                if (!name.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
