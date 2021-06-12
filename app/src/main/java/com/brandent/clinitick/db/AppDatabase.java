package com.brandent.clinitick.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.brandent.clinitick.db.converters.TimestampConverters;
import com.brandent.clinitick.db.converters.UUIDConverters;
import com.brandent.clinitick.db.daos.AppointmentDao;
import com.brandent.clinitick.db.daos.ClinicDao;
import com.brandent.clinitick.db.daos.DentistDao;
import com.brandent.clinitick.db.daos.FinanceDao;
import com.brandent.clinitick.db.daos.PatientDao;
import com.brandent.clinitick.db.daos.TaskDao;
import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.db.entities.Dentist;
import com.brandent.clinitick.db.entities.Finance;
import com.brandent.clinitick.db.entities.Patient;
import com.brandent.clinitick.db.entities.Task;

@Database(entities = {Dentist.class, Appointment.class, Clinic.class, Patient.class, Finance.class
        , Task.class}, version = 1)
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

    public abstract TaskDao taskDao();
}