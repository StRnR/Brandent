package com.pixium.clinitick.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dentists")
public class Dentist {
    @PrimaryKey
    private int dentistId;

    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String speciality;
    private final String imageName;
    private final String token;

    private final Long lastUpdated;

    private final int current;

    public Dentist(int dentistId, String firstName, String lastName, String phone
            , String speciality, String imageName, int current, String token, Long lastUpdated) {
        this.dentistId = dentistId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.speciality = speciality;
        this.imageName = imageName;
        this.current = current;
        this.token = token;
        this.lastUpdated = lastUpdated;
    }

    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getImageName() {
        return imageName;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public int getCurrent() {
        return current;
    }

    public String getToken() {
        return token;
    }
}
