package com.pixium.brandent;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClinicDao {
    @Query("SELECT * FROM clinic")
    List<Clinic> getAllClinics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertClinics(Clinic... clinics);

    @Delete
    public void deleteClinics(Clinic... clinics);
}
