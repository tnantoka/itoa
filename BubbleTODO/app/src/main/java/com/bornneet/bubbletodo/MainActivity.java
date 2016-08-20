package com.bornneet.bubbletodo;

import android.graphics.Bitmap;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    List<String> messages;
    ListView listMessages;
    MessageAdapter adapter;
    String lastMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMessages = (ListView)findViewById(R.id.list_messages);

        messages = new ArrayList<String>();
        adapter = new MessageAdapter(this, R.layout.list_message, R.id.text_message, messages);

        listMessages.setAdapter(adapter);

        final EditText editMessage = (EditText)findViewById(R.id.edit_message);
        Button buttonSubmit = (Button)findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = editMessage.getText().toString().trim();
                if (message.isEmpty()) {
                    return;
                }

                messages.add(message);
                editMessage.setText("");

                Realm realm = Realm.getDefaultInstance();

                String reply = "";

                switch (lastMessage) {
                    case "a":
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Task task = new Task(message);
                                realm.copyToRealm(task);
                            }
                        });
                        reply = list();
                        break;
                    case "d":
                        try {
                            final int id = Integer.valueOf(message);
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.where(Task.class).equalTo("id", id).findAll().deleteAllFromRealm();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        reply = list();
                        break;
                    default:
                        switch (message) {
                            case "l":
                                reply = list();
                                break;
                            case "a":
                                reply = getResources().getString(R.string.whats_the_name);
                                break;
                            case "d":
                                reply = getResources().getString(R.string.whats_the_id);
                                break;
                            default:
                                reply = getResources().getString(R.string.usage);
                                break;
                        }
                }

                messages.add(reply.trim());

                adapter.notifyDataSetChanged();
                listMessages.smoothScrollToPosition(adapter.getCount());

                lastMessage = message;
            }
        });

        messages.add(getResources().getString(R.string.may_i_help_you));

        AdView mAdView = (AdView)findViewById(R.id.view_ad);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private String list() {
        String list = "";
        RealmResults<Task> tasks = Task.all();
        if (tasks.isEmpty()) {
            list = getResources().getString(R.string.no_task_found);
        } else {
            for (Task task: Task.all()) {
                list += String.valueOf(task.id) + ": " + task.name + "\n";
            }
        }
        return list;
    }
}
