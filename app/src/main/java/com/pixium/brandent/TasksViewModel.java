package com.pixium.brandent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.Appointment;
import com.pixium.brandent.db.Patient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksViewModel extends AndroidViewModel {
    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;


    public TasksViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        patientRepository = new PatientRepository(application);
    }

    public List<Appointment> getAppointmentsByDate(Long start, Long end) throws ExecutionException, InterruptedException {
        return appointmentRepository.getByDate(start, end);
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getPatientById(id);
    }
}
