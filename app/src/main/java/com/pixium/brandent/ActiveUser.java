package com.pixium.brandent;

public class ActiveUser {
    private static ActiveUser instance;

    private final int id;

    public ActiveUser(int id) {
        this.id = id;
    }

    public static ActiveUser getInstance() {
        return instance;
    }

    public static void setActiveUser(int id) {
        instance = new ActiveUser(id);
    }

    public int getId() {
        return id;
    }
}
