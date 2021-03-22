package com.pixium.clinitick.api.models;

public class Patient {
    private String id;
    private String full_name;
    private String phone;
    private boolean is_deleted;

    public Patient(String id, String full_name, String phone, boolean is_deleted) {
        this.id = id;
        this.full_name = full_name;
        this.phone = phone;
        this.is_deleted = is_deleted;
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

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
