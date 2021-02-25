package com.pixium.brandent.api.models;

public class Appointment {

    private String id;

    private Long price;

    private String state;
    private String visit_time;
    private String disease;
    private boolean is_deleted;
    private String clinic_id;
    private String allergies;
    private String patient_id;

    public Appointment(String id, Long price, String state, String visit_time, String disease
            , boolean is_deleted, String clinic_id, String allergies, String patient_id) {
        this.id = id;
        this.price = price;
        this.state = state;
        this.visit_time = visit_time;
        this.disease = disease;
        this.is_deleted = is_deleted;
        this.clinic_id = clinic_id;
        this.allergies = allergies;
        this.patient_id = patient_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }
}
