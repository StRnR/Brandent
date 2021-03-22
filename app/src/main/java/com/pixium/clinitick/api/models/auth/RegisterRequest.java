package com.pixium.clinitick.api.models.auth;

public class RegisterRequest {
    private String phone;

    private String password;

    private String first_name;

    private String last_name;

    private String speciality;

    public RegisterRequest(String phone, String password, String first_name, String last_name, String speciality) {
        this.phone = phone;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.speciality = speciality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
