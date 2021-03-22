package com.pixium.clinitick.models;

public class TasksCalendarItem {
    private final String mInitial;
    private final String mNo;

    public TasksCalendarItem(String initial, String no) {
        mInitial = initial;
        mNo = no;
    }

    public String getInitial() {
        return mInitial;
    }

    public String getNo() {
        return mNo;
    }
}
