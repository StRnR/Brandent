package com.pixium.brandent.api.models;

public class Appointment {

    private String id;
    private String state;
    private String visit_time;
    private String disease;
    private String clinic_id;
    private String allergies;
    private String patient_id;

    private boolean is_deleted;

    private long price;

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getVisit_time() {
        return visit_time;
    }

    public String getDisease() {
        return disease;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public long getPrice() {
        return price;
    }
}
