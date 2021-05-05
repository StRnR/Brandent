package com.pixium.clinitick.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.clinitick.db.entities.Appointment;

import java.util.List;
import java.util.UUID;

@Dao
public interface AppointmentDao {
    @Query("SELECT * FROM appointment")
    List<Appointment> getAll();

    @Insert
    void insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Delete
    void delete(Appointment appointment);

    @Query("SELECT * FROM appointment WHERE modifiedAt > :updatedAt AND dentistForId=:activeUserId")
    Appointment[] getUnSynced(long updatedAt, int activeUserId);

    @Query("SELECT * FROM appointment WHERE visitTime BETWEEN :start AND :end AND isDeleted = 0 " +
            "AND dentistForId=:activeUserId ORDER BY visitTime ASC")
    List<Appointment> getByDate(long start, long end, int activeUserId);

    @Query("SELECT * FROM appointment WHERE patientForId=:arg AND dentistForId=:activeUserId " +
            "AND isDeleted = 0")
    List<Appointment> getByPatient(int arg, int activeUserId);

    @Query("SELECT price FROM appointment WHERE visitTime BETWEEN :start AND :end " +
            "AND state = :argState AND dentistForId=:activeUserId AND isDeleted = 0")
    List<Integer> getIncomeByDate(long start, long end, String argState, int activeUserId);

    @Query("SELECT * FROM appointment WHERE appointmentId=:arg AND dentistForId=:activeUserId")
    Appointment getById(int arg, int activeUserId);

    @Query("SELECT * FROM appointment WHERE uuid=:arg AND dentistForId=:activeUserId")
    Appointment getByUuid(UUID arg, int activeUserId);
}
