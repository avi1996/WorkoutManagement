package com.example.avi.workoutmanagement;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import workouts.Weights;


public class WorkoutHistory extends ActionBarActivity {

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);
        String selected = MainActivity.getSelected();
        TreeSet<Weights> weightsHistory = (TreeSet<Weights>)
                MainActivity.deserialize(MainActivity.absPath + "/weightsHistory.ser");
        ArrayList<Weights> currentHistory = new ArrayList<Weights>();
        for (Weights w: weightsHistory) {
            if (w.getName().equals(selected)) {
                currentHistory.add(w);
            }
        }
        getSupportActionBar().setTitle(currentHistory.get(0).getName() + " History");
        WeightsAdapter weightsAdapter = new WeightsAdapter(this, currentHistory);
        ListView weightsList = (ListView) findViewById(R.id.list);
        weightsList.setAdapter(weightsAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
