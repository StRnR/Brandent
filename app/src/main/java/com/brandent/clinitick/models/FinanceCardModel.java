package com.brandent.clinitick.models;

public class FinanceCardModel {
    private final int id;
    private final String title;
    private final String description;
    private final long amount;
    private final String date;
    private final boolean isExpense;

    public FinanceCardModel(int id, String title, String description, long amount, String date
            , boolean isExpense) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isExpense = isExpense;
    }

    public int getId() {
        return id;
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
