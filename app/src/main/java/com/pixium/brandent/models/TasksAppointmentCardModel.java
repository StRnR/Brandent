package com.pixium.brandent.models;

import com.pixium.brandent.db.entities.Appointment;

public class TasksAppointmentCardModel {
    private final String mPatientName;
    private final Appointment mAppointment;

    public TasksAppointmentCardModel(Appointment appointment, String patientName) {
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
