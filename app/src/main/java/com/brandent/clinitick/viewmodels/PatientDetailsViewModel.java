package com.brandent.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.db.entities.Patient;
import com.brandent.clinitick.db.repos.AppointmentRepository;
import com.brandent.clinitick.db.repos.ClinicRepository;
import com.brandent.clinitick.db.repos.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientDetailsViewModel extends AndroidViewModel {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;

    public PatientDetailsViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        appointmentRepository = new AppointmentRepository(application);
        clinicRepository = new ClinicRepository(application);
    }

    public void updatePatient(Patient patient) {
        patientRepository.update(patient);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getById(id);
    }

    public List<Patient> getPatientByPhone(String phone)
            throws ExecutionException, InterruptedException {
        return patientRepository.getPatientByNumber(phone);
    }

    public Appointment getAppointmentById(int id) throws ExecutionException, InterruptedException {
        return appointmentRepository.getById(id);
    }

    public List<Appointment> getAppointmentsByPatient(int patientId)
            throws ExecutionException, InterruptedException {
        return appointmentRepository.getByPatient(patientId);
    }

    public List<String> getPatientClinics(int id) throws ExecutionException, InterruptedException {
        List<Appointment> appointments = getAppointmentsByPatient(id);
        List<Integer> clinicIds = new ArrayList<>();
        List<String> clinicNames = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Clinic clinic = clinicRepository.getById(appointment.getClinicForId());
            if (!clinicIds.contains(clinic.getClinicId())) {
                clinicIds.add(clinic.getClinicId());
                clinicNames.add(clinic.getTitle());
            }
        }
        return clinicNames;
    }
}
