package com.pixium.brandent.api.models;

public class Finance {
    private String id;
    private String title;

    private boolean is_cost;
    private boolean is_deleted;

    private long amount;

    private String date;

    public Finance(String id, String title, boolean is_cost, boolean is_deleted, long amount, String date) {
        this.id = id;
        this.title = title;
        this.is_cost = is_cost;
        this.is_deleted = is_deleted;
        this.amount = amount;
        this.date = date;
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

    public boolean isIs_cost() {
        return is_cost;
    }

    public void setIs_cost(boolean is_cost) {
        this.is_cost = is_cost;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
