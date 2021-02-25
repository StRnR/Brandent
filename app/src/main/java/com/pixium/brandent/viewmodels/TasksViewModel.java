package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.db.repos.AppointmentRepository;
import com.pixium.brandent.db.repos.PatientRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksViewModel extends AndroidViewModel {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;


    public TasksViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        patientRepository = new PatientRepository(application);
    }

    public List<Appointment> getAppointmentsByDate(Long start, Long end) throws ExecutionException, InterruptedException {
        return appointmentRepository.getByDate(start, end);
    }

    public Appointment getAppointmentById(int id) throws ExecutionException, InterruptedException {
        return appointmentRepository.getById(id);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getById(id);
    }
}
