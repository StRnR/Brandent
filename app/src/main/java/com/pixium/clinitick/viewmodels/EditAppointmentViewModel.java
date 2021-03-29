package com.pixium.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.repos.AppointmentRepository;

import java.util.concurrent.ExecutionException;

public class EditAppointmentViewModel extends AndroidViewModel {
    private final AppointmentRepository appointmentRepository;

    public EditAppointmentViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
    }

    public Appointment getAppointmentById(int id) throws ExecutionException, InterruptedException {
        return appointmentRepository.getById(id);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }
}
