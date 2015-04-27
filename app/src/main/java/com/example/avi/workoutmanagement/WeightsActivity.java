package com.example.avi.workoutmanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.TreeSet;

import workouts.Weights;

import static com.example.avi.workoutmanagement.MainActivity.deserialize;
import static com.example.avi.workoutmanagement.MainActivity.serialize;


public class WeightsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weights);
        setNumberPicker(R.id.sets);
        setNumberPicker(R.id.reps);
        setDatePicker(R.id.date);
        setTimePicker(R.id.time);

        final EditText name = (EditText) findViewById(R.id.exercise_name);
        final EditText weight = (EditText) findViewById(R.id.weight_setting);
        final EditText muscle = (EditText) findViewById(R.id.muscle_group);
        final NumberPicker reps = (NumberPicker) findViewById(R.id.reps);
        final NumberPicker sets = (NumberPicker) findViewById(R.id.sets);
        final EditText time = (EditText) findViewById(R.id.time);
        final EditText date = (EditText) findViewById(R.id.date);

        Button create = (Button) findViewById(R.id.create_log);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onClick(View v) {
                Log.i("", "about to deserialize");
                Log.i("", MainActivity.absPath);
                TreeSet<Weights> weightsHistory = (TreeSet<Weights>) deserialize(MainActivity.absPath + "/weightsHistory.ser");
                weightsHistory.add(new Weights(name.getText().toString(), Integer.parseInt(weight.getText().toString()), muscle.getText().toString()
                        , reps.getValue(), sets.getValue(), time.getText().toString(), date.getText().toString()));
                serialize(MainActivity.absPath + "/weightsHistory.ser", weightsHistory);
                Log.i("", "New log serialized");
                finish();
            }

        });
    }

    private void startActivity(Class<?> cls) {
        Intent i = new Intent(WeightsActivity.this, cls);
        startActivity(i);
    }

    private void setDatePicker(int i) {
        final EditText date = (EditText) findViewById(i);
        Calendar currentDate = Calendar.getInstance();
        final int Year = currentDate.get(Calendar.YEAR);
        final int Month = currentDate.get(Calendar.MONTH) + 1;
        final int Day = currentDate.get(Calendar.DAY_OF_MONTH);
        if (Day < 10) {
            date.setText("" + Month + "/0" + Day + "/" + Year);
        } else {
            date.setText("" + Month + "/" + Day + "/" + Year);
        }
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(WeightsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth = selectedMonth + 1;
                        if (selectedDay < 10) {
                            date.setText("" + selectedMonth + "/0" + selectedDay + "/" + selectedYear);
                        } else {
                            date.setText("" + selectedMonth + "/" + selectedDay + "/" + selectedYear);
                        }
                    }
                }, Year, Month - 1, Day);

                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
    }

    private void setTimePicker(int i) {
        final EditText time = (EditText) findViewById(i);
        Calendar currentTime = Calendar.getInstance();
        final int Hour = currentTime.get(Calendar.HOUR_OF_DAY);
        final int Minute = currentTime.get(Calendar.MINUTE);
        if (Hour > 12) {
            if (Minute < 10) {
                time.setText((Hour - 12) + ":0" + Minute + " " + "PM");
            } else {
                time.setText((Hour - 12) + ":" + Minute + " " + "PM");
            }
        } else {
            if (Minute < 10) {
                time.setText(Hour + ":0" + Minute + " " + "AM");
            } else {
                time.setText(Hour + ":" + Minute + " " + "AM");
            }
        }
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(WeightsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour > 12) {
                            if (selectedMinute < 10) {
                                time.setText((selectedHour - 12) + ":0" + selectedMinute + " " + "PM");
                            } else {
                                time.setText((selectedHour - 12) + ":" + selectedMinute + " " + "PM");
                            }
                        } else {
                            if (selectedMinute < 10) {
                                time.setText(selectedHour + ":0" + selectedMinute + " " + "AM");
                            } else {
                                time.setText(selectedHour + ":" + selectedMinute + " " + "AM");
                            }
                        }
                    }
                }, Hour, Minute, false);
                timePicker.setTitle("Select Time");
                timePicker.show();

            }
        });
    }

    private void setNumberPicker(int i) {
        final NumberPicker np = (NumberPicker) findViewById(i);
        np.setMaxValue(1000);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weights, menu);
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
