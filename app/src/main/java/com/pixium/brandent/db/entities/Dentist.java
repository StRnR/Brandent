package com.pixium.brandent.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dentists")
public class Dentist {
    @PrimaryKey
    private int dentistId;

    private String firstName;
    private String lastName;
    private String phone;
    private String speciality;
    private String imageName;
    private String token;

    private int current;

    public Dentist(int dentistId, String firstName, String lastName, String phone
            , String speciality, String imageName, int current, String token) {

        this.dentistId = dentistId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.speciality = speciality;
        this.imageName = imageName;
        this.current = current;
        this.token = token;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }

    public int getDentistId() {
        return dentistId;
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

    public int getCurrent() {
        return current;
    }

    public String getToken() {
        return token;
    }
}
