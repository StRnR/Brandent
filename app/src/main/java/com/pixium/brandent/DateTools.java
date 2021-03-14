package com.pixium.brandent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class DateTools {
    public static final String oldLastUpdated = "1970-10-10 00:00:00.000";
    public static final String apiTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String apiOldTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String apiDateFormat = "yyyy-MM-dd";

    public static String stringFromTimestamp(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static Long timestampFromString(String dateStr, String dateFormat) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        calendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(dateStr)));
        return calendar.getTimeInMillis();
    }

    public static String timestampToSimpleJalali(long timestamp) {
        PersianDate persianDate = new PersianDate(timestamp);
        PersianDateFormat pdf = new PersianDateFormat("y/n/d");
        return pdf.format(persianDate);
    }

    // year in 4 digits and month 1-12 day 1-30
    public static long persianDateToTimestamp(int year, int month, int day, int hour, int minute, int second) {
        PersianDate persianDate = new PersianDate();
        persianDate.setShYear(year);
        persianDate.setShMonth(month);
        persianDate.setShDay(day);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, persianDate.getGrgYear());
        calendar.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTimeInMillis();
    }
}
