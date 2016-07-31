package com.bornneet.editcode;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        projects = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projects);
        listProjects.setAdapter(adapter);
        listProjects.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String projct = projects.get(i);
            }
        });

        loadProjects();
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
            Intent intent = new Intent(this, NewActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 1) {
            String name = data.getStringExtra("name");
            File project = new File(projectsDir(), name);
            project.mkdir();
            loadProjects();
        }
    }

    private File projectsDir() {
        return new File(getFilesDir(), "projects");
    }

    private void loadProjects() {
        projects.clear();
        File dir = projectsDir();
        dir.mkdir();
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory()) {
                String name = file.getName();
                projects.add(name);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
