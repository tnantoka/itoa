package com.bornneet.editcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> projects;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listProjects = (ListView)findViewById(R.id.list_projects);
        TextView textEmpty = (TextView)findViewById(R.id.text_empty);
        listProjects.setEmptyView(textEmpty);

        loadProjects();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projects);
        listProjects.setAdapter(adapter);
        listProjects.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String projct = projects.get(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadProjects() {
        projects = new ArrayList<String>();
        File dir = new File(getFilesDir(), "entries");
        dir.mkdir();
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                projects.add(file.getName());
            }
        }
    }
}
