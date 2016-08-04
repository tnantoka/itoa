package com.bornneet.editcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TabHost;

/**
 * Created by tnantoka on 8/1/16.
 */
public class DetailActivity extends AppCompatActivity implements TextWatcher {

    Project project;

    EditText editHTML;
    EditText editCSS;
    EditText editJS;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        project = new Project(name);
        project.load();

        setTitle(project.name);

        editHTML = (EditText)findViewById(R.id.tab_layout_html).findViewById(R.id.edit_text);
        editCSS = (EditText)findViewById(R.id.tab_layout_css).findViewById(R.id.edit_text);
        editJS = (EditText)findViewById(R.id.tab_layout_js).findViewById(R.id.edit_text);
        webView = (WebView)findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);

        TabHost tabHost = (TabHost)findViewById(R.id.tab_host);

        tabHost.setup();
        TabHost.TabSpec spec;

        spec = tabHost
                .newTabSpec("html")
                .setIndicator("HTML")
                .setContent(R.id.tab_layout_html);
        tabHost.addTab(spec);

        spec = tabHost
                .newTabSpec("css")
                .setIndicator("CSS")
                .setContent(R.id.tab_layout_css);
        tabHost.addTab(spec);

        spec = tabHost
                .newTabSpec("js")
                .setIndicator("JS")
                .setContent(R.id.tab_layout_js);
        tabHost.addTab(spec);

        spec = tabHost
                .newTabSpec("web")
                .setIndicator("Web")
                .setContent(R.id.tab_layout_web);
        tabHost.addTab(spec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s == "web") {
                    if (webView.getUrl() == null) {
                        webView.loadUrl(project.url());
                    } else {
                        webView.reload();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        editHTML.setText(project.html);
        editCSS.setText(project.css);
        editJS.setText(project.js);

        editHTML.removeTextChangedListener(this);
        editCSS.removeTextChangedListener(this);
        editJS.removeTextChangedListener(this);

        editHTML.addTextChangedListener(this);
        editCSS.addTextChangedListener(this);
        editJS.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            project.destroy();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        project.html = editHTML.getText().toString();
        project.css = editCSS.getText().toString();
        project.js = editJS.getText().toString();
        project.save();
    }
}
