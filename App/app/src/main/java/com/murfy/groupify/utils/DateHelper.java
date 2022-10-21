package com.murfy.groupify.utils;

import android.icu.text.SimpleDateFormat;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static String getTodayDateFormatted(){
        return new SimpleDateFormat("EEEE, MMM d").format(new Date());
    }
}
