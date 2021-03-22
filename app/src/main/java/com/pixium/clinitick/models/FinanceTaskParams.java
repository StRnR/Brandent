package com.pixium.clinitick.models;

public class FinanceTaskParams {
    public Long start;
    public Long end;
    public String type;

    public FinanceTaskParams(Long start, Long end, String type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }
}