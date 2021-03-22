package com.pixium.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Clinic;

import java.util.List;

public class ClinicWithAppointments {
    @Embedded
    public Clinic clinic;
    @Relation(
            parentColumn = "clinicId",
            entityColumn = "clinicForId"
    )
    public List<Appointment> clinicAppointments;
}
