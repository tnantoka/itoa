package com.bornneet.bubbletodo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tnantoka on 8/19/16.
 */
public class MessageAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private int textViewResourceId;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(resource, parent, false);

        String message = getItem(position);

        TextView textMessage = (TextView)convertView.findViewById(textViewResourceId);
        textMessage.setText(message);

        int color = position %  2 == 0 ? R.color.colorAccent : R.color.colorPrimary;
        textMessage.setBackgroundColor(ContextCompat.getColor(context, color));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)textMessage.getLayoutParams();
        int rule = position %  2 == 0 ? RelativeLayout.ALIGN_PARENT_LEFT : RelativeLayout.ALIGN_PARENT_RIGHT;
        params.addRule(rule);
        textMessage.setLayoutParams(params);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
