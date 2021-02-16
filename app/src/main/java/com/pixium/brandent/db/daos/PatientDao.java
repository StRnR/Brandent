package com.pixium.brandent.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.brandent.db.entities.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Insert
    void insert(Patient patient);

    @Update
    void update(Patient patient);

    @Delete
    void delete(Patient patient);

    @Query("SELECT * FROM patient WHERE dentistForId=:activeUserId")
    LiveData<List<Patient>> getAllPatients(int activeUserId);

    @Query("SELECT * FROM patient WHERE name LIKE '%' || :arg || '%' AND dentistForId=:activeUserId")
    List<Patient> findPatientsByName(String arg, int activeUserId);

    @Query("SELECT * FROM patient WHERE phone LIKE :arg AND dentistForId=:activeUserId")
    List<Patient> getPatientByNumber(String arg, int activeUserId);

    @Query("SELECT * FROM patient WHERE patientId=:arg AND dentistForId=:activeUserId")
    Patient getPatientById(int arg, int activeUserId);

}
