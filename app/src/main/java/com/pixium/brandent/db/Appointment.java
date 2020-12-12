package com.pixium.brandent.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity
public class Appointment {
    @PrimaryKey
    @NonNull
    public UUID appointmentId;
    public UUID clinicForId;
    public UUID patientForId;

    public boolean deleted = false;

    public int price;

    public Date modifiedAt;
    public Date visitTime;

    public String disease;
    public String notes;
    public String state;

    public Appointment(UUID appointmentId, UUID clinicForId, UUID patientForId, Date modifiedAt, Date visitTime, int price, String disease, String notes, String state) {
        if (appointmentId == null) {
            this.appointmentId = UUID.randomUUID();
        } else {
            this.appointmentId = appointmentId;
        }

        this.clinicForId = clinicForId;
        this.patientForId = patientForId;

        if (modifiedAt == null) {
            this.modifiedAt = new Date();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.visitTime = visitTime;

        this.price = price;

        this.disease = disease;
        this.notes = notes;
        this.state = state;
    }
}
