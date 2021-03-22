package com.pixium.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Dentist;

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
