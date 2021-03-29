package com.pixium.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Patient;

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