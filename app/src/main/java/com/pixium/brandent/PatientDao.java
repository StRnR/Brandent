package com.pixium.brandent;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patient")
    List<Patient> getAllPatients();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPatients(Patient... patients);

    @Query("SELECT * from patient ")

    @Delete
    public void deletePatients(Patient... patients);

}
