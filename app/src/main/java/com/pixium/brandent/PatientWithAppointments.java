package com.pixium.brandent;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PatientWithAppointments {
    @Embedded
    public Patient patient;
    @Relation(
            parentColumn = "patientId",
            entityColumn = "patientForId"
    )
    public List<Appointment> patientAppointments;
}
