package com.pixium.brandent.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PatientDao {
    @Insert
    void insert(Patient patient);

    @Update
    void update(Patient patient);

    @Delete
    void delete(Patient patient);

    @Query("SELECT * FROM patient")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patient WHERE name LIKE '%' || :arg || '%'")
    List<Patient> findPatientsByName(String arg);

    @Query("SELECT * FROM patient WHERE phone LIKE :arg")
    List<Patient> getPatientByNumber(String arg);

}
