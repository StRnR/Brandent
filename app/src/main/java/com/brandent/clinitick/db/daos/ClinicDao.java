package com.brandent.clinitick.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.brandent.clinitick.db.entities.Clinic;

import java.util.List;
import java.util.UUID;

@Dao
public interface ClinicDao {
    @Insert
    void insert(Clinic clinic);

    @Update
    void update(Clinic clinic);

    @Delete
    void delete(Clinic clinic);

    @Query("SELECT * FROM clinics WHERE modifiedAt > :updatedAt AND dentistForId=:activeUserId")
    Clinic[] getUnSynced(long updatedAt, int activeUserId);

    @Query("SELECT * FROM clinics WHERE dentistForId=:activeUserId AND isDeleted = 0")
    LiveData<List<Clinic>> getAllClinicsLive(int activeUserId);

    @Query("SELECT * FROM clinics WHERE dentistForId=:activeUserId AND isDeleted = 0")
    List<Clinic> getAllClinics(int activeUserId);

    @Query("SELECT * FROM clinics WHERE title LIKE :argTitle AND dentistForId=:activeUserId " +
            "AND isDeleted = 0")
    List<Clinic> getClinicByTitle(String argTitle, int activeUserId);

    @Query("SELECT * FROM clinics WHERE clinicId=:arg AND dentistForId=:activeUserId")
    Clinic getById(int arg, int activeUserId);

    @Query("SELECT * FROM clinics WHERE uuid=:arg AND dentistForId=:activeUserId")
    Clinic getByUuid(UUID arg, int activeUserId);
}
