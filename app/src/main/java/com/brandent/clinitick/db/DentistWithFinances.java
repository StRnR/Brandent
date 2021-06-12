package com.brandent.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.brandent.clinitick.db.entities.Dentist;
import com.brandent.clinitick.db.entities.Finance;

import java.util.List;

public class DentistWithFinances {
    @Embedded
    public Dentist dentist;
    @Relation(
            parentColumn = "dentistId",
            entityColumn = "dentistForId"
    )
    public List<Finance> dentistFinances;
}
