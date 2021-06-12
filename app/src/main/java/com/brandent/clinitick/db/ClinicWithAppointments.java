package com.brandent.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.db.entities.Clinic;

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
