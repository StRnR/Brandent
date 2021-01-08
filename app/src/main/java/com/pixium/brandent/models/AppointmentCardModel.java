package com.pixium.brandent.models;

import com.pixium.brandent.db.entities.Appointment;

public class AppointmentCardModel {
    private String mPatientName;
    private Appointment mAppointment;

    public AppointmentCardModel(Appointment appointment, String patientName) {
        this.mAppointment = appointment;
        this.mPatientName = patientName;
    }


    public String getPatientName() {
        return mPatientName;
    }


    public Appointment getAppointment() {
        return mAppointment;
    }
}
