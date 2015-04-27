package workouts;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Avi on 4/22/15.
 */
public class Weights implements Serializable, Comparable<Weights>{

    private String name;
    private int weight;
    private String muscle;
    private int reps;
    private int sets;
    private int hour;
    private int minute;
    private boolean AM;
    private int month;
    private int day;
    private int year;
    private String time;

    public Weights(String name, int weight, String muscle, int reps, int sets, String time, String date) {
        this.name = name;
        this.weight = weight;
        this.muscle = muscle;
        this.reps = reps;
        this.sets = sets;
        this.time = time;

        char[] timePieces = time.toCharArray();

        String tempHour = "";
        int colonIndex = 0;
        for (int i = 0; i <= 2; i++) {
            if (timePieces[i] == ':') {
                colonIndex = i;
                break;
            } else {
                tempHour += timePieces[i];
            }
        }
        hour = Integer.parseInt(tempHour);

        String tempMinute = "" + timePieces[colonIndex+1] + timePieces[colonIndex+2];
        minute = Integer.parseInt(tempMinute);

        if (timePieces[colonIndex + 4] == 'A') {
            AM = true;
        } else {
            AM = false;
        }

        char[] datePieces = date.toCharArray();

        String tempMonth = "";
        int slashIndex = 0;
        for (int i = 0; i <= 2; i++) {
            if (datePieces[i] == '/') {
                slashIndex = i;
                break;
            } else {
                tempMonth += datePieces[i];
            }
        }
        month = Integer.parseInt(tempMonth);

        String tempDay = "" + datePieces[slashIndex+1] + datePieces[slashIndex+2];
        day = Integer.parseInt(tempDay);

        String tempYear = "" + datePieces[slashIndex+4] + datePieces[slashIndex+5]
                + datePieces[slashIndex+6] + datePieces[slashIndex+7];
        year = Integer.parseInt(tempYear);



    }


    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getMuscle() {
        return muscle;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isAM() {
        return AM;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public Calendar getDate() {
        return new GregorianCalendar(getYear(), getMonth(), getDay(), getHour(), getMinute());
    }

//    @Override
//    public int hashCode() {
//
//    }

    public static HashMap<Integer, String> getMonths() {
        HashMap<Integer, String> months = new HashMap<Integer, String>();
        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");
        months.put(4, "April");
        months.put(5, "May");
        months.put(6, "June");
        months.put(7, "July");
        months.put(8, "August");
        months.put(9, "September");
        months.put(10, "October");
        months.put(11, "November");
        months.put(12, "December");

        return months;

    }

    public String getCal() {
        HashMap<Integer, String> months = getMonths();
        Integer tempDay = this.day;
        Integer tempYear = this.year;

        String toReturn = "" + months.get(this.month) + " " + tempDay.toString() + ", "
                + tempYear.toString() + " @ " + this.time;

        return toReturn;

    }

    public String getTime() {
        return time;
    }

    public int compareTo(Weights w) {
        return this.getDate().compareTo(w.getDate());
    }
}
