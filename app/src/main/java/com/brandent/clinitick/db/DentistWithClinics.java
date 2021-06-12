package com.brandent.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.db.entities.Dentist;

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
