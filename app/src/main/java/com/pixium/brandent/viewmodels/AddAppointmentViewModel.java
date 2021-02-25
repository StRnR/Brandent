package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.db.repos.AppointmentRepository;
import com.pixium.brandent.db.repos.ClinicRepository;
import com.pixium.brandent.db.repos.PatientRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddAppointmentViewModel extends AndroidViewModel {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;

    public AddAppointmentViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        appointmentRepository = new AppointmentRepository(application);
        clinicRepository = new ClinicRepository(application);
    }

    public void insertPatient(Patient patient) {
        patientRepository.insert(patient);
    }

    public void updatePatient(Patient patient) {
        patientRepository.update(patient);
    }

    public List<Patient> getPatientByNumber(String string) throws ExecutionException
            , InterruptedException {
        return patientRepository.getPatientByNumber(string);
    }

    public void insertAppointment(Appointment appointment) {
        appointmentRepository.insert(appointment);
    }

    public List<Clinic> getClinicByTitle(String string) throws ExecutionException
            , InterruptedException {
        return clinicRepository.getClinicBytTitle(string);
    }

}
