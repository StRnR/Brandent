package com.brandent.clinitick.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int patientId;

    private final int dentistForId;

    private final UUID uuid;

    private final Long modifiedAt;

    private final String name;
    private final String phone;

    private final int isDeleted;

    public Patient(int dentistForId, UUID uuid, Long modifiedAt, String name, String phone, int isDeleted) {
        if (uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
        }

        if (modifiedAt == null) {
            this.modifiedAt = System.currentTimeMillis();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.dentistForId = dentistForId;
        this.name = name;
        this.phone = phone;
        this.isDeleted = isDeleted;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDentistForId() {
        return dentistForId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
