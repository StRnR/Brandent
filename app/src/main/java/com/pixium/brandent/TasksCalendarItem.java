package com.pixium.brandent;

public class TasksCalendarItem {
    private String mInitial;
    private String mNo;

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
