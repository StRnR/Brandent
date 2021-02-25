package com.pixium.brandent.api.models;

public class Patient {
    private String id;
    private String full_name;
    private String phone;

    public Patient(String id, String full_name, String phone) {
        this.id = id;
        this.full_name = full_name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
