package com.pixium.brandent.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int patientId;

    private UUID uuid;

    private Long modifiedAt;

    private String name;
    private String phone;

    public Patient(UUID uuid, Long modifiedAt, String name, String phone) {
        this.uuid = uuid;

        if (modifiedAt == null) {
            this.modifiedAt = System.currentTimeMillis();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.name = name;
        this.phone = phone;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
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
