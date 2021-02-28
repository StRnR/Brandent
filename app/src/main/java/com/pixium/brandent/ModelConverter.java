package com.pixium.brandent;

import com.pixium.brandent.api.models.Appointment;
import com.pixium.brandent.api.models.Clinic;
import com.pixium.brandent.api.models.Finance;
import com.pixium.brandent.api.models.Patient;

import java.text.ParseException;
import java.util.UUID;

public class ModelConverter {

    public static Clinic[] clinicsToSync(com.pixium.brandent.db.entities.Clinic[] dbClinics) {
        Clinic[] res = new Clinic[dbClinics.length];
        for (int i = 0; i < dbClinics.length; i++) {
            res[i] = new Clinic(dbClinics[i].getUuid().toString(), dbClinics[i].getTitle()
                    , dbClinics[i].getAddress(), dbClinics[i].getColor(), false);
        }
        return res;
    }


    public static com.pixium.brandent.db.entities.Clinic[] clinicsFromSync(Clinic[] clinics) {
        com.pixium.brandent.db.entities.Clinic[] res =
                new com.pixium.brandent.db.entities.Clinic[clinics.length];
        for (int i = 0; i < clinics.length; i++) {
            res[i] = new com.pixium.brandent.db.entities.Clinic(ActiveUser.getInstance().getId()
                    , UUID.fromString(clinics[i].getId()), Long.MIN_VALUE, clinics[i].getTitle()
                    , clinics[i].getAddress(), clinics[i].getColor());
        }
        return res;
    }


    public static Patient[] patientsToSync(com.pixium.brandent.db.entities.Patient[] dbPatients) {
        Patient[] res = new Patient[dbPatients.length];
        for (int i = 0; i < dbPatients.length; i++) {
            res[i] = new Patient(dbPatients[i].getUuid().toString(), dbPatients[i].getName()
                    , dbPatients[i].getPhone(), false);
        }
        return res;
    }


    public static com.pixium.brandent.db.entities.Patient[] patientsFromSync(Patient[] patients) {
        com.pixium.brandent.db.entities.Patient[] res =
                new com.pixium.brandent.db.entities.Patient[patients.length];
        for (int i = 0; i < patients.length; i++) {
            res[i] = new com.pixium.brandent.db.entities.Patient(ActiveUser.getInstance().getId()
                    , UUID.fromString(patients[i].getId()), Long.MIN_VALUE
                    , patients[i].getFull_name(), patients[i].getPhone());
        }
        return res;
    }


    public static Appointment[] appointmentsToSync(String[] clinicUuids, String[] patientUuids
            , com.pixium.brandent.db.entities.Appointment[] dbAppointments) {
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


    public static com.pixium.brandent.db.entities.Appointment[] appointmentsFromSync(int[] clinicIds
            , int[] patientIds, Appointment[] appointments) throws ParseException {
        com.pixium.brandent.db.entities.Appointment[] res =
                new com.pixium.brandent.db.entities.Appointment[appointments.length];
        for (int i = 0; i < appointments.length; i++) {
            res[i] = new com.pixium.brandent.db.entities.Appointment(ActiveUser.getInstance().getId()
                    , UUID.fromString(appointments[i].getId()), Long.MIN_VALUE, clinicIds[i]
                    , patientIds[i], DateTools.timestampFromString(appointments[i].getVisit_time()
                    , DateTools.apiOldTimeFormat), appointments[i].getPrice()
                    , appointments[i].getDisease(), appointments[i].getState()
                    , appointments[i].isIs_deleted() ? 1 : 0);
        }
        return res;
    }


    public static Finance[] financesToSync(com.pixium.brandent.db.entities.Finance[] dbFinances) {
        Finance[] res = new Finance[dbFinances.length];
        for (int i = 0; i < dbFinances.length; i++) {
            res[i] = new Finance(dbFinances[i].getUuid().toString(), dbFinances[i].getTitle()
                    , dbFinances[i].getType().equals("EXPENSE")
                    , dbFinances[i].getIsDeleted() == 1, dbFinances[i].getPrice()
                    , DateTools.stringFromTimestamp(dbFinances[i].getDate(), DateTools.apiDateFormat));
        }
        return res;
    }


    public static com.pixium.brandent.db.entities.Finance[] financesFromSync(Finance[] finances)
            throws ParseException {
        com.pixium.brandent.db.entities.Finance[] res =
                new com.pixium.brandent.db.entities.Finance[finances.length];
        for (int i = 0; i < finances.length; i++) {
            res[i] = new com.pixium.brandent.db.entities.Finance(ActiveUser.getInstance().getId()
                    , UUID.fromString(finances[i].getId()), Long.MIN_VALUE
                    , DateTools.timestampFromString(finances[i].getDate(), DateTools.apiDateFormat)
                    , finances[i].getAmount(), finances[i].getTitle()
                    , finances[i].isIs_cost() ? "EXPENSE" : "INCOME"
                    , finances[i].isIs_deleted() ? 1 : 0);
        }
        return res;
    }
}
