package com.pixium.clinitick.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.clinitick.db.entities.Dentist;
import com.pixium.clinitick.db.entities.Finance;

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
