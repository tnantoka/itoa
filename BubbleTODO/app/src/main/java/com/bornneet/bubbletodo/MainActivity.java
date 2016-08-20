package com.bornneet.bubbletodo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Task task = new Task("name");
                realm.copyToRealm(task);
            }
        });

        for (Task task: Task.all()) {
            Log.d("task", String.valueOf(task.id) + task.name);
        }

        ListView listMessages = (ListView)findViewById(R.id.list_messages);

        List<String> messages = new ArrayList<String>();
        MessageAdapter adapter = new MessageAdapter(this, R.layout.list_message, R.id.text_message, messages);

        listMessages.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            messages.add("test " + String.valueOf(i));
        }
        listMessages.smoothScrollToPosition(adapter.getCount());

        AdView mAdView = (AdView)findViewById(R.id.view_ad);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
//        mAdView.loadAd(adRequest);
    }
}
