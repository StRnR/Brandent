package com.pixium.brandent.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.db.entities.Finance;

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
