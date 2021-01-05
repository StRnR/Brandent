package com.pixium.brandent;

public class AppointmentIncomeTaskParams {
    Long start;
    Long end;
    String state;

    AppointmentIncomeTaskParams(Long start, Long end, String state) {
        this.start = start;
        this.end = end;
        this.state = state;
    }
}