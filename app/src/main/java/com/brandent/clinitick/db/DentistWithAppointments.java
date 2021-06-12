package com.brandent.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.db.entities.Dentist;

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
