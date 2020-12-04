package com.pixium.brandent;

import androidx.room.Embedded;
import androidx.room.Relation;

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
