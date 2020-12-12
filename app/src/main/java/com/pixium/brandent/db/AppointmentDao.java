package com.pixium.brandent.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface AppointmentDao {
    @Query("SELECT * FROM appointment")
    List<Appointment> getAll();

    @Query("SELECT * FROM appointment WHERE visitTime BETWEEN :from AND :to")
    List<Appointment> findAppointmentsBetweenDates(Date from, Date to);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAppointments(Appointment... appointments);

    @Update
    public void updateAppointments(Appointment... appointments);

    @Delete
    public void deleteAppointments(Appointment... appointments);

}
