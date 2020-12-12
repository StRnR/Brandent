package com.pixium.brandent.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity
public class Clinic {
    @PrimaryKey
    @NonNull
    public UUID clinicId;

    public Date modifiedAt;

    public String color;
    public String address;
    public String title;

    public Clinic(UUID clinicId, Date modifiedAt, String title, String address, String color) {
        if (clinicId == null) {
            this.clinicId = UUID.randomUUID();
        } else {
            this.clinicId = clinicId;
        }

        if (modifiedAt == null) {
            this.modifiedAt = new Date();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.title = title;
        this.address = address;
        this.color = color;
    }
}
