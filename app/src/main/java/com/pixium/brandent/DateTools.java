package com.pixium.brandent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DateTools {

    public static final String apiTimeFormat = "yyyy-MM-dd HH:mm:ss.SSSS";
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
}
