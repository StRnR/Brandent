package com.pixium.brandent;

public class FinanceTaskParams {
    Long start;
    Long end;
    String type;

    FinanceTaskParams(Long start, Long end, String type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }
}