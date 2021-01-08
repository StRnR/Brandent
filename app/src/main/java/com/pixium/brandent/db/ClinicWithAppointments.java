package com.pixium.brandent.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;

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
