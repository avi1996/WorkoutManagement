package com.example.avi.workoutmanagement;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import workouts.Weights;

public class MainActivity extends ActionBarActivity {
    private static String selected;

    public static String getSelected() {
        return selected;
    }

    public HashMap<String, String> getWorkoutHistory() {
        return workoutHistory;
    }

    public void setWorkoutHistory(HashMap<String, String> workoutHistory) {
        this.workoutHistory = workoutHistory;
    }

    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        absPath = getFilesDir().getAbsolutePath();

        File serialized = new File(absPath + "/Serialized");
        if (!serialized.exists()) {
            serialized.mkdir();
            serialize(absPath + "/workoutHistory.ser", workoutHistory);
            serialize(absPath + "/weightsHistory.ser", weightsHistory);

        } else {
            weightsHistory = (TreeSet<Weights>) deserialize(absPath + "/weightsHistory.ser");
            for (Weights w: weightsHistory) {
                workoutHistory.put(w.getName(), w.getCal());
            }
            serialize(absPath + "/workoutHistory.ser", workoutHistory);
            updateWorkoutHistory();
        }
        final ArrayAdapter<CharSequence> workout_adapter = new ArrayAdapter<CharSequence>(MainActivity.this,
                android.R.layout.simple_spinner_item, workouts);
        workout_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Button create = (Button) (findViewById(R.id.create_log));
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "Clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.choose_workout, null);
                final Spinner spinner = (Spinner) layout.findViewById(R.id.choose_workout);
                spinner.setAdapter(workout_adapter);
                builder.setView(layout);
                builder.setTitle("Please select a workout.");
                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked SELECT button
                        String selection = (String) spinner.getSelectedItem();
                        switch (selection) {
                            case "Weights":
                                startActivity(WeightsActivity.class);
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        workoutHistory = (HashMap<String, String>) deserialize(absPath + "/workoutHistory.ser");
        Log.i("", "Line 1 has passed");
        ArrayList<String> temp = new ArrayList<String>();
        temp.addAll(workoutHistory.keySet());
        String[] tempArray = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            tempArray[i] = temp.get(i).toString();
        }
        final ArrayAdapter<CharSequence> previous_workouts = new ArrayAdapter<CharSequence>(MainActivity.this,
                android.R.layout.simple_spinner_item, tempArray);
        previous_workouts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Button history = (Button) (findViewById(R.id.view_history));
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "Clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.choose_workout, null);
                final Spinner spinner = (Spinner) layout.findViewById(R.id.choose_workout);
                spinner.setAdapter(previous_workouts);
                builder.setView(layout);
                builder.setTitle("Please choose a workout.");
                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked SELECT button
                        selected = (String) spinner.getSelectedItem();
                        startActivity(WorkoutHistory.class);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private HashMap<String, String> workoutHistory = new HashMap<String, String>();
    protected static String absPath;
    private String[] workouts = new String[]{"Walk", "Weights", "Run", "Bike", "Yoga",
            "Elliptical", "Cross Training", "Cardio", "Swim", "Tennis", "Dance", "Soccer"};

    private TreeSet<Weights> weightsHistory = new TreeSet<Weights>();

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        absPath = getFilesDir().getAbsolutePath();

        File serialized = new File(absPath + "/Serialized");
        if (!serialized.exists()) {
            serialized.mkdir();
            serialize(absPath + "/workoutHistory.ser", workoutHistory);
            serialize(absPath + "/weightsHistory.ser", weightsHistory);

        } else {
            weightsHistory = (TreeSet<Weights>) deserialize(absPath + "/weightsHistory.ser");
             for (Weights w: weightsHistory) {
                workoutHistory.put(w.getName(), w.getCal());
            }
            serialize(absPath + "/workoutHistory.ser", workoutHistory);
            updateWorkoutHistory();
        }
        final ArrayAdapter<CharSequence> workout_adapter = new ArrayAdapter<CharSequence>(MainActivity.this,
                android.R.layout.simple_spinner_item, workouts);
        workout_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Button create = (Button) (findViewById(R.id.create_log));
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "Clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.choose_workout, null);
                final Spinner spinner = (Spinner) layout.findViewById(R.id.choose_workout);
                spinner.setAdapter(workout_adapter);
                builder.setView(layout);
                builder.setTitle("Please select a workout.");
                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked SELECT button
                        String selection = (String) spinner.getSelectedItem();
                        switch (selection) {
                            case "Weights":
                                startActivity(WeightsActivity.class);
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        workoutHistory = (HashMap<String, String>) deserialize(absPath + "/workoutHistory.ser");
        Log.i("", "Line 1 has passed");
        ArrayList<String> temp = new ArrayList<String>();
        temp.addAll(workoutHistory.keySet());
        String[] tempArray = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            tempArray[i] = temp.get(i).toString();
        }
        final ArrayAdapter<CharSequence> previous_workouts = new ArrayAdapter<CharSequence>(MainActivity.this,
                android.R.layout.simple_spinner_item, tempArray);
        previous_workouts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Button history = (Button) (findViewById(R.id.view_history));
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "Clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.choose_workout, null);
                final Spinner spinner = (Spinner) layout.findViewById(R.id.choose_workout);
                spinner.setAdapter(previous_workouts);
                builder.setView(layout);
                builder.setTitle("Please choose a workout.");
                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked SELECT button
                        selected = (String) spinner.getSelectedItem();
                        startActivity(WorkoutHistory.class);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void startActivity(Class<?> cls) {
        Intent i = new Intent(MainActivity.this, cls);
        startActivity(i);
    }

    private void updateWorkoutHistory() {
        Log.i("updating", "started");
        workoutHistory = (HashMap<String, String>) deserialize(absPath + "/workoutHistory.ser");
        TableLayout workoutTable = (TableLayout) findViewById(R.id.workout_history);
        android.widget.TableRow.LayoutParams p = new android.widget.TableRow.LayoutParams();
        p.column = dpToPixel(1, this); // right-margin = 10dp
        if (workoutHistory.isEmpty()) {
            Log.i("updating", "workoutHistory IS EMPTY!!!");
        }
        for (String s : workoutHistory.keySet()) {
            TableRow row = new TableRow(this);

            TextView name = new TextView(this);
            name.setText(s);
            Log.i("updating", s);
            row.addView(name);

            TextView date = new TextView(this);


            date.setText("     " +
                    "                     " + workoutHistory.get(s));
            date.setLayoutParams(p);
            row.addView(date);

            workoutTable.addView(row);
        }
    }

    private static Float scale;
    public static int dpToPixel(int dp, Context context) {
        if (scale == null)
            scale = context.getResources().getDisplayMetrics().density;
        return (int) ((float) dp * scale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static void serialize(String path, Object o) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
        }

    }

    public static Object deserialize(String path) {
        Object deserialized;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("Deserialization", "about read object");
            deserialized = ois.readObject();
            Log.i("Deserialization", "read object");
            fis.close();
            ois.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            Log.i("Deserialization", "ioe exception");
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            Log.i("Deserialization", "ClassNotFound exception");
            return null;
        }
        return deserialized;
    }

}
