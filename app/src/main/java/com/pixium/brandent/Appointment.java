package com.pixium.brandent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Appointment {
    @PrimaryKey
    public String appointmentId;

    @ColumnInfo
    public String disease;

    @ColumnInfo
    public boolean deleted;

    @ColumnInfo
    public String modifiedAt;

    @ColumnInfo
    public String notes;

    @ColumnInfo
    public int price;

    @ColumnInfo
    public String state;

    @ColumnInfo
    public String clinicForId;

    @ColumnInfo
    public String patientForId;

    @ColumnInfo
    public String visitTime;
}
