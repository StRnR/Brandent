package com.pixium.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Patient;
import com.pixium.clinitick.db.entities.Task;
import com.pixium.clinitick.db.repos.AppointmentRepository;
import com.pixium.clinitick.db.repos.PatientRepository;
import com.pixium.clinitick.db.repos.TaskRepository;
import com.pixium.clinitick.models.TasksCardModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksViewModel extends AndroidViewModel {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final TaskRepository taskRepository;

    public TasksViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        patientRepository = new PatientRepository(application);
        taskRepository = new TaskRepository(application);
    }

    public List<Appointment> getAppointmentsByDate(Long start, Long end) throws ExecutionException
            , InterruptedException {
        return appointmentRepository.getByDate(start, end);
    }

    public List<Task> getTasksByDate(Long start, Long end) throws ExecutionException
            , InterruptedException {
        return taskRepository.getByDate(start, end);
    }

    public Appointment getAppointmentById(int id) throws ExecutionException, InterruptedException {
        return appointmentRepository.getById(id);
    }

    public Task getTaskById(int id) throws ExecutionException, InterruptedException {
        return taskRepository.getById(id);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getById(id);
    }

    public List<TasksCardModel> getTasksCardModels(List<Appointment> appointments, List<Task> tasks)
            throws ExecutionException, InterruptedException {
        List<TasksCardModel> res = new ArrayList<>();
        int tasksSize = 0;
        int appointmentsSize = 0;
        if (tasks != null) {
            tasksSize += tasks.size();
        }
        if (appointments != null) {
            appointmentsSize += appointments.size();
        }

        int appointmentsIndex = 0;
        int tasksIndex = 0;
        for (int i = 0; i < appointmentsSize + tasksSize; i++) {
            if (appointmentsIndex < appointmentsSize && tasksIndex < tasksSize) {
                if (appointments.get(appointmentsIndex).getVisitTime() <= tasks.get(tasksIndex).getTime()) {
                    Appointment current = appointments.get(appointmentsIndex);
                    res.add(new TasksCardModel(getPatientById(current.getPatientForId()).getName()
                            , current.getTitle(), current.getState(), current.getVisitTime()
                            , current.getAppointmentId()));
                    appointmentsIndex++;
                } else {
                    Task current = tasks.get(tasksIndex);
                    res.add(new TasksCardModel(current.getTitle(), "", current.getState()
                            , current.getTime(), current.getTaskId()));
                    tasksIndex++;
                }
            } else if (appointmentsIndex >= appointmentsSize) {
                Task current = tasks.get(tasksIndex);
                res.add(new TasksCardModel(current.getTitle(), "", current.getState()
                        , current.getTime(), current.getTaskId()));
                tasksIndex++;
            } else {
                Appointment current = appointments.get(appointmentsIndex);
                res.add(new TasksCardModel(getPatientById(current.getPatientForId()).getName()
                        , current.getTitle(), current.getState(), current.getVisitTime()
                        , current.getAppointmentId()));
                appointmentsIndex++;
            }
        }
        return res;
    }
}
