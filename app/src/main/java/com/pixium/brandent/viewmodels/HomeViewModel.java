package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.db.repos.AppointmentRepository;
import com.pixium.brandent.db.repos.ClinicRepository;
import com.pixium.brandent.db.repos.DentistRepository;
import com.pixium.brandent.db.repos.PatientRepository;

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
