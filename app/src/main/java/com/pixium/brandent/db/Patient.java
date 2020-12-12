package com.pixium.brandent.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity
public class Patient {
    @PrimaryKey
    @NonNull
    public UUID patientId;

    public Date modifiedAt;

    public String name;
    public String phone;
}
