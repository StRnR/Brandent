package com.pixium.brandent.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.brandent.db.entities.Appointment;

import java.util.List;

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

    @Query("SELECT * FROM appointment WHERE visitTime BETWEEN :start AND :end ORDER BY visitTime ASC")
    List<Appointment> getByDate(long start, long end);

    @Query("SELECT * FROM appointment WHERE patientForId=:arg")
    List<Appointment> getByPatient(int arg);

    @Query("SELECT price FROM appointment WHERE visitTime BETWEEN :start AND :end " +
            "AND state = :argState")
    List<Integer> getIncomeByDate(long start, long end, String argState);

    @Query("SELECT * FROM appointment WHERE appointmentId=:arg")
    Appointment getById(int arg);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppointments(Appointment... appointments);

    @Update
    void updateAppointments(Appointment... appointments);

    @Delete
    void deleteAppointments(Appointment... appointments);

}
