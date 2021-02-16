package com.pixium.brandent.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int patientId;

    private int dentistForId;

    private UUID uuid;

    private Long modifiedAt;

    private String name;
    private String phone;

    public Patient(int dentistForId, UUID uuid, Long modifiedAt, String name, String phone) {
        this.uuid = uuid;

        if (modifiedAt == null) {
            this.modifiedAt = System.currentTimeMillis();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.dentistForId = dentistForId;
        this.name = name;
        this.phone = phone;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
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
}
