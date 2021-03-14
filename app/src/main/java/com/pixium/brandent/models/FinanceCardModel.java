package com.pixium.brandent.models;

public class FinanceCardModel {
    private final String title;
    private final String description;
    private final long amount;
    private final String date;
    private final boolean isExpense;

    public FinanceCardModel(String title, String description, long amount, String date
            , boolean isExpense) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isExpense = isExpense;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public boolean isExpense() {
        return isExpense;
    }
}
