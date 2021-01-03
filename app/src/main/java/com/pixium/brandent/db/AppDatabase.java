package com.pixium.brandent.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Appointment.class, Clinic.class, Patient.class, Finance.class}, version = 1)
@TypeConverters({TimestampConverters.class, UUIDConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract AppointmentDao appointmentDao();

    public abstract PatientDao patientDao();

    public abstract ClinicDao clinicDao();

    public abstract FinanceDao financeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}