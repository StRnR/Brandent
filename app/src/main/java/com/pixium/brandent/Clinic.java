package com.pixium.brandent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Clinic {
    @PrimaryKey
    public String clinicId;

    @ColumnInfo
    public int title;

    @ColumnInfo
    public String color;

    @ColumnInfo
    public String address;

    @ColumnInfo
    public String modifiedAt;
}
