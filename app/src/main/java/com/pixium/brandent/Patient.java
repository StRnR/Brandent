package com.pixium.brandent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Patient {
    @PrimaryKey
    public String patientId;

    @ColumnInfo
    public String modifiedAt;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String phone;
}
