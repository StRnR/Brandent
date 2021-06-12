package com.brandent.clinitick;

import com.brandent.clinitick.api.models.Appointment;
import com.brandent.clinitick.api.models.Clinic;
import com.brandent.clinitick.api.models.Finance;
import com.brandent.clinitick.api.models.Patient;
import com.brandent.clinitick.api.models.Task;

import java.text.ParseException;
import java.util.UUID;

public class ModelConverter {

    public static Clinic[] clinicsToSync(com.brandent.clinitick.db.entities.Clinic[] dbClinics) {
        Clinic[] res = new Clinic[dbClinics.length];
        for (int i = 0; i < dbClinics.length; i++) {
            res[i] = new Clinic(dbClinics[i].getUuid().toString(), dbClinics[i].getTitle()
                    , dbClinics[i].getAddress(), dbClinics[i].getColor(), false);
        }
        return res;
    }


    public static com.brandent.clinitick.db.entities.Clinic[] clinicsFromSync(Clinic[] clinics) {
        com.brandent.clinitick.db.entities.Clinic[] res =
                new com.brandent.clinitick.db.entities.Clinic[clinics.length];
        for (int i = 0; i < clinics.length; i++) {
            res[i] = new com.brandent.clinitick.db.entities.Clinic(ActiveUser.getInstance().getId()
                    , UUID.fromString(clinics[i].getId()), Long.MIN_VALUE, clinics[i].getTitle()
                    , clinics[i].getAddress(), clinics[i].getColor(), clinics[i].isDeleted() ? 1 : 0);
        }
        return res;
    }


    public static Patient[] patientsToSync(com.brandent.clinitick.db.entities.Patient[] dbPatients) {
        Patient[] res = new Patient[dbPatients.length];
        for (int i = 0; i < dbPatients.length; i++) {
            res[i] = new Patient(dbPatients[i].getUuid().toString(), dbPatients[i].getName()
                    , dbPatients[i].getPhone(), false);
        }
        return res;
    }


    public static com.brandent.clinitick.db.entities.Patient[] patientsFromSync(Patient[] patients) {
        com.brandent.clinitick.db.entities.Patient[] res =
                new com.brandent.clinitick.db.entities.Patient[patients.length];
        for (int i = 0; i < patients.length; i++) {
            res[i] = new com.brandent.clinitick.db.entities.Patient(ActiveUser.getInstance().getId()
                    , UUID.fromString(patients[i].getId()), Long.MIN_VALUE, patients[i].getFull_name()
                    , patients[i].getPhone(), patients[i].isDeleted() ? 1 : 0);
        }
        return res;
    }


    public static Appointment[] appointmentsToSync(String[] clinicUuids, String[] patientUuids
            , com.brandent.clinitick.db.entities.Appointment[] dbAppointments) {
        Appointment[] res = new Appointment[dbAppointments.length];
        for (int i = 0; i < dbAppointments.length; i++) {
            res[i] = new Appointment(dbAppointments[i].getUuid().toString()
                    , dbAppointments[i].getPrice(), dbAppointments[i].getState()
                    , DateTools.stringFromTimestamp(dbAppointments[i].getVisitTime(), DateTools.apiTimeFormat)
                    , dbAppointments[i].getTitle(), dbAppointments[i].getIsDeleted() == 1
                    , clinicUuids[i], null, patientUuids[i]);
        }
        return res;
    }


    public static com.brandent.clinitick.db.entities.Appointment[] appointmentsFromSync(int[] clinicIds
            , int[] patientIds, Appointment[] appointments) throws ParseException {
        com.brandent.clinitick.db.entities.Appointment[] res =
                new com.brandent.clinitick.db.entities.Appointment[appointments.length];
        for (int i = 0; i < appointments.length; i++) {
            res[i] = new com.brandent.clinitick.db.entities.Appointment(ActiveUser.getInstance().getId()
                    , UUID.fromString(appointments[i].getId())
                    , DateTools.timestampFromString(DateTools.oldLastUpdated, DateTools.apiTimeFormat)
                    , clinicIds[i], patientIds[i]
                    , DateTools.timestampFromString(appointments[i].getVisit_time()
                    , DateTools.apiOldTimeFormat), appointments[i].getPrice()
                    , appointments[i].getDisease(), appointments[i].getState()
                    , appointments[i].isIs_deleted() ? 1 : 0);
        }
        return res;
    }


    public static Task[] tasksToSync(String[] clinicUuids
            , com.brandent.clinitick.db.entities.Task[] dbTasks) {
        Task[] res = new Task[dbTasks.length];
        for (int i = 0; i < dbTasks.length; i++) {
            res[i] = new Task(dbTasks[i].getUuid().toString(), dbTasks[i].getTitle()
                    , dbTasks[i].getState(), DateTools.stringFromTimestamp(dbTasks[i].getTime()
                    , DateTools.apiTimeFormat), clinicUuids[i]
                    , dbTasks[i].getIsDeleted() == 1);
        }
        return res;
    }


    public static com.brandent.clinitick.db.entities.Task[] tasksFromSync(int[] clinicIds
            , Task[] tasks) throws ParseException {
        com.brandent.clinitick.db.entities.Task[] res =
                new com.brandent.clinitick.db.entities.Task[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            res[i] = new com.brandent.clinitick.db.entities.Task(clinicIds[i]
                    , ActiveUser.getInstance().getId(), UUID.fromString(tasks[i].getId())
                    , DateTools.timestampFromString(DateTools.oldLastUpdated, DateTools.apiTimeFormat)
                    , DateTools.timestampFromString(tasks[i].getTask_date(), DateTools.apiTimeFormat)
                    , tasks[i].getTitle(), tasks[i].getState(), tasks[i].isIs_deleted() ? 1 : 0);
        }
        return res;
    }


    public static Finance[] financesToSync(com.brandent.clinitick.db.entities.Finance[] dbFinances) {
        Finance[] res = new Finance[dbFinances.length];
        for (int i = 0; i < dbFinances.length; i++) {
            res[i] = new Finance(dbFinances[i].getUuid().toString(), dbFinances[i].getTitle()
                    , dbFinances[i].getType().equals("EXPENSE")
                    , dbFinances[i].getIsDeleted() == 1, dbFinances[i].getPrice()
                    , DateTools.stringFromTimestamp(dbFinances[i].getDate(), DateTools.apiDateFormat));
        }
        return res;
    }


    public static com.brandent.clinitick.db.entities.Finance[] financesFromSync(Finance[] finances)
            throws ParseException {
        com.brandent.clinitick.db.entities.Finance[] res =
                new com.brandent.clinitick.db.entities.Finance[finances.length];
        for (int i = 0; i < finances.length; i++) {
            res[i] = new com.brandent.clinitick.db.entities.Finance(ActiveUser.getInstance().getId()
                    , UUID.fromString(finances[i].getId()), Long.MIN_VALUE
                    , DateTools.timestampFromString(finances[i].getDate(), DateTools.apiDateFormat)
                    , finances[i].getAmount(), finances[i].getTitle()
                    , finances[i].isIs_cost() ? "EXPENSE" : "INCOME"
                    , finances[i].isIs_deleted() ? 1 : 0);
        }
        return res;
    }
}
