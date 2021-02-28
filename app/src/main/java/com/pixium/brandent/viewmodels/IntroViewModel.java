package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pixium.brandent.api.models.auth.AuthResponse;
import com.pixium.brandent.api.models.auth.LoginRequest;
import com.pixium.brandent.api.models.sync.SyncRequest;
import com.pixium.brandent.api.models.sync.SyncResponse;
import com.pixium.brandent.api.repos.AuthRepository;
import com.pixium.brandent.api.repos.SyncRepository;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.db.entities.Finance;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.db.repos.AppointmentRepository;
import com.pixium.brandent.db.repos.ClinicRepository;
import com.pixium.brandent.db.repos.DentistRepository;
import com.pixium.brandent.db.repos.FinanceRepository;
import com.pixium.brandent.db.repos.PatientRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class IntroViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final SyncRepository syncRepository;
    private final DentistRepository dentistRepository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final FinanceRepository financeRepository;
    private final LiveData<List<Dentist>> allDentists;

    public IntroViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance();
        syncRepository = SyncRepository.getInstance();
        dentistRepository = new DentistRepository(application);
        clinicRepository = new ClinicRepository(application);
        patientRepository = new PatientRepository(application);
        appointmentRepository = new AppointmentRepository(application);
        financeRepository = new FinanceRepository(application);
        allDentists = dentistRepository.getAllLive();
    }

    public LiveData<AuthResponse> loginDentist(LoginRequest loginRequest) {
        return authRepository.loginDentist(loginRequest);
    }

    public LiveData<SyncResponse> sync(SyncRequest syncRequest, String token) {
        return syncRepository.sync(syncRequest, token);
    }

    public Clinic[] getUnsyncedClinics(long lastUpdated) throws ExecutionException
            , InterruptedException {
        return clinicRepository.getUnsynced(lastUpdated);
    }

    public Patient[] getUnsyncedPatients(long lastUpdated) throws ExecutionException
            , InterruptedException {
        return patientRepository.getUnsynced(lastUpdated);
    }

    public Appointment[] getUnsyncedAppointments(long lastUpdated) throws ExecutionException
            , InterruptedException {
        return appointmentRepository.getUnsynced(lastUpdated);
    }

    public Finance[] getUnsyncedFinances(long lastUpdated) throws ExecutionException
            , InterruptedException {
        return financeRepository.getUnsynced(lastUpdated);
    }

    public int[] getClinicForIds(com.pixium.brandent.api.models.Appointment[] appointments)
            throws ExecutionException, InterruptedException {
        int[] res = new int[appointments.length];
        for (int i = 0; i < appointments.length; i++) {
            res[i] = getClinicByUuid(UUID.fromString(appointments[i].getClinic_id())).getClinicId();
        }
        return res;
    }

    public String[] getClinicForUuidStrings(Appointment[] appointments)
            throws ExecutionException, InterruptedException {
        String[] res = new String[appointments.length];
        for (int i = 0; i < appointments.length; i++) {
            res[i] = getClinicById(appointments[i].getClinicForId()).getUuid().toString();
        }
        return res;
    }

    public int[] getPatientForIds(com.pixium.brandent.api.models.Appointment[] appointments)
            throws ExecutionException, InterruptedException {
        int[] res = new int[appointments.length];
        for (int i = 0; i < appointments.length; i++) {
            res[i] = getPatientByUuid(UUID.fromString(appointments[i].getPatient_id())).getPatientId();
        }
        return res;
    }

    public String[] getPatientForUuidStrings(Appointment[] appointments)
            throws ExecutionException, InterruptedException {
        String[] res = new String[appointments.length];
        for (int i = 0; i < appointments.length; i++) {
            res[i] = getPatientById(appointments[i].getPatientForId()).getUuid().toString();
        }
        return res;
    }

    public void insertDentist(Dentist dentist) {
        dentistRepository.insert(dentist);
    }

    public void updateDentist(Dentist dentist) {
        dentistRepository.update(dentist);
    }

    public void insertClinics(Clinic... clinics) throws ExecutionException, InterruptedException {
        for (Clinic clinic : clinics) {
            Clinic existingClinic = getClinicByUuid(clinic.getUuid());
            if (existingClinic == null) {
                clinicRepository.insert(clinic);
            } else {
                clinic.setClinicId(existingClinic.getClinicId());
                clinicRepository.update(clinic);
            }
        }
    }

    public void insertPatients(Patient... patients) throws ExecutionException, InterruptedException {
        for (Patient patient : patients) {
            Patient existingPatient = getPatientByUuid(patient.getUuid());
            if (existingPatient == null) {
                patientRepository.insert(patient);
            } else {
                patient.setPatientId(existingPatient.getPatientId());
                patientRepository.update(patient);
            }
        }
    }

    public void insertAppointments(Appointment... appointments)
            throws ExecutionException, InterruptedException {
        for (Appointment appointment : appointments) {
            Appointment existingAppointment = getAppointmentByUuid(appointment.getUuid());
            if (existingAppointment == null) {
                appointmentRepository.insert(appointment);
            } else {
                appointment.setAppointmentId(existingAppointment.getAppointmentId());
                appointmentRepository.update(appointment);
            }
        }
    }

    public void insertFinances(Finance... finances) throws ExecutionException, InterruptedException {
        for (Finance finance : finances) {
            Finance existingFinance = getFinanceByUuid(finance.getUuid());
            if (existingFinance == null) {
                financeRepository.insert(finance);
            } else {
                finance.setFinanceId(existingFinance.getFinanceId());
                financeRepository.update(finance);
            }
        }
    }

    public LiveData<List<Dentist>> getAllDentistsLive() {
        return allDentists;
    }

    public List<Dentist> getAllDentists() throws ExecutionException, InterruptedException {
        return dentistRepository.getAll();
    }

    public Dentist getCurrentDentist() throws ExecutionException, InterruptedException {
        return dentistRepository.getCurrent();
    }

    public Dentist getDentistById(int id) throws ExecutionException, InterruptedException {
        return dentistRepository.getById(id);
    }

    public Clinic getClinicById(int id) throws ExecutionException, InterruptedException {
        return clinicRepository.getById(id);
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getById(id);
    }

    public Clinic getClinicByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return clinicRepository.getByUuid(uuid);
    }

    public Patient getPatientByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return patientRepository.getByUuid(uuid);
    }

    public Appointment getAppointmentByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return appointmentRepository.getByUuid(uuid);
    }

    public Finance getFinanceByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return financeRepository.getByUuid(uuid);
    }
}
