package com.brandent.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.db.entities.Dentist;
import com.brandent.clinitick.db.entities.Patient;
import com.brandent.clinitick.db.repos.AppointmentRepository;
import com.brandent.clinitick.db.repos.ClinicRepository;
import com.brandent.clinitick.db.repos.DentistRepository;
import com.brandent.clinitick.db.repos.PatientRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends AndroidViewModel {
    private final DentistRepository dentistRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        clinicRepository = new ClinicRepository(application);
        patientRepository = new PatientRepository(application);
        dentistRepository = new DentistRepository(application);
    }

    public Dentist getDentistById(int id) throws ExecutionException, InterruptedException {
        return dentistRepository.getById(id);
    }

    public List<Appointment> getAppointmentsByDate(Long start, Long end)
            throws ExecutionException, InterruptedException {
        return appointmentRepository.getByDate(start, end);
    }

    public Clinic getClinicById(int id) throws ExecutionException, InterruptedException {
        return clinicRepository.getById(id);
    }

    public Appointment getAppointmentById(int id) throws ExecutionException, InterruptedException {
        return appointmentRepository.getById(id);
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getById(id);
    }
}
