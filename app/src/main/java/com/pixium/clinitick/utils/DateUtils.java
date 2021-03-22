package com.pixium.clinitick.utils;

import saman.zamani.persiandate.PersianDate;

public class DateUtils {
    public static String getPersianStringDate(Long timestamp) {
        PersianDate persianDate = new PersianDate(timestamp);
        return persianDate.getShDay() + " " + persianDate.monthName() + " " +
                persianDate.getShYear();
    }
}
