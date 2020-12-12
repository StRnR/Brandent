package com.pixium.brandent.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.brandent.db.Appointment;
import com.pixium.brandent.db.Clinic;

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
