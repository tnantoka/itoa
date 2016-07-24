package com.bornneet.fetchcurrency;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    TextView textRate;
    TextView textUpdated;

    ProgressBar progressBar;
    FrameLayout layoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textRate = (TextView)findViewById(R.id.text_rate);
        textUpdated = (TextView)findViewById(R.id.text_updated);

        textRate.setText("");
        textUpdated.setText("");

        Button buttonRefresh = (Button)findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshRate();
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        layoutProgress = (FrameLayout)findViewById(R.id.layout_progress);
        hideProgressBar();

        refreshRate();
    }

    private void hideProgressBar() {
        layoutProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        layoutProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void refreshRate() {
        showProgressBar();
        getLoaderManager().restartLoader(0, null, this);
    }

    private void updateTextUpdated() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getApplicationContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());

        String text = dateFormat.format(date) + " " + timeFormat.format(date);
        textUpdated.setText(text);
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        return new RateLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        hideProgressBar();
        try {
            JSONObject query = data.getJSONObject("query");
            JSONObject results = query.getJSONObject("results");
            JSONObject rate = results.getJSONObject("rate");
            String rateString = rate.getString("Rate");

            textRate.setText(rateString);
            updateTextUpdated();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
    }
}
