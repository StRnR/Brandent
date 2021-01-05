package com.pixium.brandent.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppointmentDao {
    @Query("SELECT * FROM appointment")
    List<Appointment> getAll();

    @Insert
    public void insert(Appointment appointment);

    @Update
    public void update(Appointment appointment);

    @Delete
    public void delete(Appointment appointment);

    @Query("SELECT * FROM appointment WHERE visitTime BETWEEN :start AND :end ORDER BY visitTime ASC")
    public List<Appointment> getByDate(long start, long end);

    @Query("SELECT price FROM appointment WHERE visitTime BETWEEN :start AND :end " +
            "AND state = :argState")
    public List<Integer> getIncomeByDate(long start, long end, String argState);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAppointments(Appointment... appointments);

    @Update
    public void updateAppointments(Appointment... appointments);

    @Delete
    public void deleteAppointments(Appointment... appointments);

}
