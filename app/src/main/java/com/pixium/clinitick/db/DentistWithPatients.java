package com.pixium.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.clinitick.db.entities.Dentist;
import com.pixium.clinitick.db.entities.Patient;

import java.util.List;

public class DentistWithPatients {
    @Embedded
    public Dentist dentist;
    @Relation(
            parentColumn = "dentistId",
            entityColumn = "dentistForId"
    )
    public List<Patient> dentistPatients;
}
