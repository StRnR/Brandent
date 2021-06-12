package com.brandent.clinitick.api.models;

public class Clinic {
    private String id;
    private String title;
    private String address;
    private String color;
    private boolean is_deleted;

    public Clinic(String id, String title, String address, String color, boolean is_deleted) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.color = color;
        this.is_deleted = is_deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDeleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
