package com.pixium.brandent.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Dentist;

import java.util.List;

public class DentistWithAppointments {
    @Embedded
    public Dentist dentist;
    @Relation(
            parentColumn = "dentistId",
            entityColumn = "dentistForId"
    )
    public List<Appointment> dentistAppointments;
}
