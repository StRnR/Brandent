package com.pixium.brandent.api.models.sync;

import com.pixium.brandent.api.models.Appointment;
import com.pixium.brandent.api.models.Clinic;
import com.pixium.brandent.api.models.Finance;
import com.pixium.brandent.api.models.Patient;
import com.pixium.brandent.api.models.Task;

public class SyncResponse {
    private String timestamp;
    private String message;

    private Clinic[] clinics;
    private Patient[] patients;
    private Finance[] finances;
    private Appointment[] appointments;
    private Task[] tasks;

    public SyncResponse(String timestamp, String message, Clinic[] clinics, Patient[] patients
            , Finance[] finances, Appointment[] appointments, Task[] tasks) {
        this.timestamp = timestamp;
        this.message = message;
        this.clinics = clinics;
        this.patients = patients;
        this.finances = finances;
        this.appointments = appointments;
        this.tasks = tasks;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Clinic[] getClinics() {
        return clinics;
    }

    public void setClinics(Clinic[] clinics) {
        this.clinics = clinics;
    }

    public Patient[] getPatients() {
        return patients;
    }

    public void setPatients(Patient[] patients) {
        this.patients = patients;
    }

    public Finance[] getFinances() {
        return finances;
    }

    public void setFinances(Finance[] finances) {
        this.finances = finances;
    }

    public Appointment[] getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointment[] appointments) {
        this.appointments = appointments;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }
}
