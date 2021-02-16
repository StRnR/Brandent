package com.pixium.brandent.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Dentist;

import java.util.List;

public class DentistWithClinics {
    @Embedded
    public Dentist dentist;
    @Relation(
            parentColumn = "dentistId",
            entityColumn = "dentistForId"
    )
    public List<Clinic> dentistClinics;
}
