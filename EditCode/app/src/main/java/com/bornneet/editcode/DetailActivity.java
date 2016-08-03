package com.bornneet.editcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TabHost;

/**
 * Created by tnantoka on 8/1/16.
 */
public class DetailActivity extends AppCompatActivity {

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
        
        editHTML.setText(project.html);
        editCSS.setText(project.css);
        editJS.setText(project.js);

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
                    webView.loadUrl(project.url());
                }
            }
        });
    }
}
