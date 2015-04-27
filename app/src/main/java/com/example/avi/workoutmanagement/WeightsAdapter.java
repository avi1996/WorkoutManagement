package com.example.avi.workoutmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

import workouts.Weights;

/**
 * Created by Avi on 4/23/15.
 */
public class WeightsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Weights> items;

    public WeightsAdapter(Context context, ArrayList<Weights> items) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.weights_listitem, parent, false);
        }

        TextView weight = (TextView) convertView.findViewById(R.id.weight);
        TextView sets = (TextView) convertView.findViewById(R.id.sets);
        TextView reps = (TextView) convertView.findViewById(R.id.reps);
        TextView dateTime = (TextView) convertView.findViewById(R.id.date_time);

        Weights currentWeight = items.get(position);

            Log.i("", ((Integer) currentWeight.getWeight()).toString());

        weight.setText(((Integer) currentWeight.getWeight()).toString());
        sets.setText(((Integer) currentWeight.getSets()).toString());
        reps.setText(((Integer) currentWeight.getReps()).toString());
        dateTime.setText(currentWeight.getCal());

        return convertView;
    }
}
