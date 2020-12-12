package com.pixium.brandent.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pixium.brandent.db.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patient")
    List<Patient> getAllPatients();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPatients(Patient... patients);

    @Delete
    public void deletePatients(Patient... patients);

}
