package com.bornneet.editcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    List<Project> projects;
    ArrayAdapter<Project> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Project.parentDir = projectsDir();

        ListView listProjects = (ListView)findViewById(R.id.list_projects);
        TextView textEmpty = (TextView)findViewById(R.id.text_empty);
        listProjects.setEmptyView(textEmpty);
        listProjects.setOnItemClickListener(this);

        projects = new ArrayList<Project>();

        adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_list_item_1, projects);
        listProjects.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        if (id == R.id.action_delete_all) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_sure)
                    .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (Project project: projects) {
                                project.destroy();
                            }
                            loadProjects();
                        }
                    })
                    .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })                    .show();

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
            new Project(name).create();
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
                projects.add(new Project(name));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Project project = projects.get(i);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("name", project.name);
        startActivity(intent);
    }
}
