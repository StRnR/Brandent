package com.brandent.clinitick.api.models;

public class Task {
    private String id;
    private String title;
    private String state;
    private String task_date;
    private String clinic_id;

    private boolean is_deleted;

    public Task(String id, String title, String state, String task_date, String clinic_id
            , boolean is_deleted) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.task_date = task_date;
        this.clinic_id = clinic_id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
