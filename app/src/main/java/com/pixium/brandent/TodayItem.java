package com.pixium.brandent;

public class TodayItem {
    private final String mColor;
    private final String mClinicTitle;
    private int mAppointmentNumber;

    public TodayItem(String color, String clinicTitle, int appointmentNumber) {
        this.mColor = color;
        this.mClinicTitle = clinicTitle;
        this.mAppointmentNumber = appointmentNumber;
    }

    public String getColor() {
        return mColor;
    }

    public String getClinicTitle() {
        return mClinicTitle;
    }

    public int getAppointmentNumber() {
        return mAppointmentNumber;
    }

    public void setAppointmentNumber(int number) {
        this.mAppointmentNumber = number;
    }
}
