package com.pixium.brandent.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "clinics")
public class Clinic {
    @PrimaryKey(autoGenerate = true)
    private int clinicId;

    private Long modifiedAt;

    private UUID uuid;

    private String color;
    private String address;
    private String title;


    public Clinic(UUID uuid, Long modifiedAt, String title, String address, String color) {
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

        this.title = title;
        this.address = address;
        this.color = color;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getColor() {
        return color;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }
}
