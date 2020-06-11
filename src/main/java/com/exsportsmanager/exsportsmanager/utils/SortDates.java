package com.exsportsmanager.exsportsmanager.utils;

import com.exsportsmanager.exsportsmanager.models.Sport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class SortDates implements Comparator<com.exsportsmanager.exsportsmanager.models.Date> {

    @Override
    public int compare(com.exsportsmanager.exsportsmanager.models.Date date1,
                       com.exsportsmanager.exsportsmanager.models.Date date2) {
        int compareStartDay = date1.getStartDay().compareTo(date2.getStartDay());

        SimpleDateFormat s = new SimpleDateFormat("MMMMM");
        Date s1 = null;
        Date s2 = null;
        try {
            s1 = s.parse(date1.getStartMonth());
            s2 = s.parse(date2.getStartMonth());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compareStartMonth = s1.compareTo(s2);

        if (compareStartMonth == 0) {
            return ((compareStartDay == 0) ? compareStartMonth : compareStartDay);
        } else {
            return compareStartMonth;
        }
    }
}