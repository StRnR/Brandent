package com.pixium.brandent.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Appointment.class, Clinic.class, Patient.class}, version = 1)
@TypeConverters({DateConverters.class, UUIDConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppointmentDao appointmentDao();

    public abstract PatientDao patientDao();

    public abstract ClinicDao clinicDao();
}