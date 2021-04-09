package com.pixium.clinitick.models;

public class TasksCardModel {
    private final String title;
    private final String description;
    private final String state;

    private final long time;

    private final int id;

    public TasksCardModel(String title, String description, String state, long time, int id) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.time = time;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public long getTime() {
        return time;
    }

    public int getId() {
        return id;
    }
}
