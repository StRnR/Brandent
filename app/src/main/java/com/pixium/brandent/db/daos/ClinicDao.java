package com.pixium.brandent.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.brandent.db.entities.Clinic;

import java.util.List;

@Dao
public interface ClinicDao {
    @Insert
    void insert(Clinic clinic);

    @Update
    void update(Clinic clinic);

    @Delete
    void delete(Clinic clinic);

    @Query("SELECT * FROM clinics WHERE dentistForId=:activeUserId")
    LiveData<List<Clinic>> getAllClinicsLive(int activeUserId);

    @Query("SELECT * FROM clinics WHERE dentistForId=:activeUserId")
    List<Clinic> getAllClinics(int activeUserId);

    @Query("SELECT * FROM clinics WHERE title LIKE :argTitle AND dentistForId=:activeUserId")
    List<Clinic> getClinicByTitle(String argTitle, int activeUserId);

    @Query("SELECT * FROM clinics WHERE clinicId=:arg AND dentistForId=:activeUserId")
    Clinic getById(int arg, int activeUserId);

    @Query("SELECT * FROM clinics WHERE clinicId=:argId AND dentistForId=:activeUserId")
    LiveData<Clinic> getClinicById(int argId, int activeUserId);

    @Query("SELECT * FROM clinics WHERE color LIKE :argColor AND dentistForId=:activeUserId")
    List<Clinic> loadClinicByColor(String argColor, int activeUserId);
}
