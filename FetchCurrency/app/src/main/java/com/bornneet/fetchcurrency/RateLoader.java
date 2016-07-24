package com.bornneet.fetchcurrency;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tnantoka on 7/23/16.
 */
public class RateLoader extends AsyncTaskLoader<JSONObject> {

    public RateLoader(Context context) {
        super(context);
    }

    @Override
    public JSONObject loadInBackground() {
        try {
            // select * from yahoo.finance.xchange where pair in ("USDJPY")
            String urlString = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28%22USDJPY%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
