package com.pixium.clinitick.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.pixium.clinitick.DateTools;

import java.util.UUID;

@Entity
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    private int appointmentId;

    private final int clinicForId;
    private final int patientForId;
    private final int dentistForId;

    private final UUID uuid;

    private final Long price;
    private final Long modifiedAt;
    private final Long visitTime;

    private final String title;
    private final String state;

    private final int isDeleted;

    public Appointment(int dentistForId, UUID uuid, Long modifiedAt, int clinicForId, int patientForId
            , Long visitTime, Long price, String title, String state, int isDeleted) {
        this.isDeleted = isDeleted;
        if (uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
        }

        this.dentistForId = dentistForId;
        this.clinicForId = clinicForId;
        this.patientForId = patientForId;

        if (modifiedAt == null) {
            this.modifiedAt = System.currentTimeMillis();
        } else {
            this.modifiedAt = modifiedAt;
        }

        if (visitTime == null) {
            this.visitTime = DateTools.noVisitTime;
        } else {
            this.visitTime = visitTime;
        }

        if (price == null) {
            this.price = 0L;
        } else {
            this.price = price;
        }

        this.title = title;
        this.state = state;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getClinicForId() {
        return clinicForId;
    }

    public int getPatientForId() {
        return patientForId;
    }

    public int getDentistForId() {
        return dentistForId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getPrice() {
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

    public int getIsDeleted() {
        return isDeleted;
    }
}
