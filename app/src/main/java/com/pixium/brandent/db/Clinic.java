package com.pixium.brandent.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "clinics")
public class Clinic {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date modifiedAt;

    private UUID uuid;

    private String color;
    private String address;
    private String title;


    public Clinic(UUID uuid, Date modifiedAt, String title, String address, String color) {
        if (uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public Date getModifiedAt() {
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
