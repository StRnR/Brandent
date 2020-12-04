package com.pixium.brandent;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Appointment.class, Clinic.class, Patient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppointmentDao appointmentDao();

    public abstract PatientDao patientDao();

    public abstract ClinicDao clinicDao();
}