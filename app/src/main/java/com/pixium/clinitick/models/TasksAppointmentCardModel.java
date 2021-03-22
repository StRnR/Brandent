package com.pixium.clinitick.models;

import com.pixium.clinitick.db.entities.Appointment;

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
