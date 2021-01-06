package com.pixium.brandent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.Appointment;
import com.pixium.brandent.db.Clinic;
import com.pixium.brandent.db.Patient;

import java.nio.file.Path;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends AndroidViewModel {
    private AppointmentRepository appointmentRepository;
    private ClinicRepository clinicRepository;
    private PatientRepository patientRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        clinicRepository = new ClinicRepository(application);
        patientRepository = new PatientRepository(application);
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
        return patientRepository.getPatientById(id);
    }
}
