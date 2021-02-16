package com.pixium.brandent.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Dentist;

import java.util.List;

@Dao
public interface DentistDao {
    @Insert
    void insert(Dentist dentist);

    @Update
    void update(Dentist dentist);

    @Delete
    void delete(Dentist dentist);

    @Query("SELECT * FROM dentists")
    LiveData<List<Dentist>> getAllLive();

    @Query("SELECT * FROM dentists")
    List<Dentist> getAll();

    @Query("SELECT * FROM dentists WHERE current = 1")
    Dentist getCurrent();

    @Query("SELECT * FROM dentists WHERE dentistId=:arg")
    Dentist getById(int arg);
}
