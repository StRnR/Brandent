package com.pixium.brandent.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.brandent.db.Appointment;
import com.pixium.brandent.db.Patient;

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
