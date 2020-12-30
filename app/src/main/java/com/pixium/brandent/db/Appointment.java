package com.pixium.brandent.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    private int appointmentId;

    private int clinicForId;
    private int patientForId;

    private UUID uuid;

    private int price;

    private Long modifiedAt;
    private Long visitTime;

    private String title;
    private String state;

    public Appointment(UUID uuid, Long modifiedAt, int clinicForId, int patientForId, Long visitTime, int price, String title, String state) {
        if (uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
        }

        this.clinicForId = clinicForId;
        this.patientForId = patientForId;

        if (modifiedAt == null) {
            this.modifiedAt = System.currentTimeMillis();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.visitTime = visitTime;

        this.price = price;

        this.title = title;
        this.state = state;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getClinicForId() {
        return clinicForId;
    }

    public int getPatientForId() {
        return patientForId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPrice() {
        return price;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public Long getVisitTime() {
        return visitTime;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }
}
