package com.pixium.brandent.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClinicDao {
    @Insert
    void insert(Clinic clinic);

    @Update
    void update(Clinic clinic);

    @Delete
    void delete(Clinic clinic);

    @Query("SELECT * FROM clinics")
    LiveData<List<Clinic>> getAllClinicsLive();

    @Query("SELECT * FROM clinics")
    List<Clinic> getAllClinics();

    @Query("SELECT * FROM clinics WHERE title LIKE :argTitle")
    List<Clinic> getClinicByTitle(String argTitle);

    @Query("SELECT * FROM clinics WHERE clinicId=:arg")
    Clinic getById(int arg);

    @Query("SELECT * FROM clinics WHERE clinicId=:argId")
    LiveData<Clinic> getClinicById(int argId);

    @Query("SELECT * FROM clinics WHERE color LIKE :argColor")
    List<Clinic> loadClinicByColor(String argColor);
}
