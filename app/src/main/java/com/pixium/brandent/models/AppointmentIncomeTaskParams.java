package com.pixium.brandent.models;

public class AppointmentIncomeTaskParams {
    public Long start;
    public Long end;
    public String state;

    public AppointmentIncomeTaskParams(Long start, Long end, String state) {
        this.start = start;
        this.end = end;
        this.state = state;
    }
}