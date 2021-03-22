package com.pixium.clinitick.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pixium.clinitick.db.converters.TimestampConverters;
import com.pixium.clinitick.db.converters.UUIDConverters;
import com.pixium.clinitick.db.daos.AppointmentDao;
import com.pixium.clinitick.db.daos.ClinicDao;
import com.pixium.clinitick.db.daos.DentistDao;
import com.pixium.clinitick.db.daos.FinanceDao;
import com.pixium.clinitick.db.daos.PatientDao;
import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Clinic;
import com.pixium.clinitick.db.entities.Dentist;
import com.pixium.clinitick.db.entities.Finance;
import com.pixium.clinitick.db.entities.Patient;

@Database(entities = {Dentist.class, Appointment.class, Clinic.class, Patient.class, Finance.class}
        , version = 1)
@TypeConverters({TimestampConverters.class, UUIDConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract DentistDao dentistDao();

    public abstract AppointmentDao appointmentDao();

    public abstract PatientDao patientDao();

    public abstract ClinicDao clinicDao();

    public abstract FinanceDao financeDao();
}