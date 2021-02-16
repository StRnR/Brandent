package com.pixium.brandent.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pixium.brandent.db.converters.TimestampConverters;
import com.pixium.brandent.db.converters.UUIDConverters;
import com.pixium.brandent.db.daos.AppointmentDao;
import com.pixium.brandent.db.daos.ClinicDao;
import com.pixium.brandent.db.daos.DentistDao;
import com.pixium.brandent.db.daos.FinanceDao;
import com.pixium.brandent.db.daos.PatientDao;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.db.entities.Finance;
import com.pixium.brandent.db.entities.Patient;

@Database(entities = {Dentist.class, Appointment.class, Clinic.class, Patient.class, Finance.class}, version = 1)
@TypeConverters({TimestampConverters.class, UUIDConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract DentistDao dentistDao();

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