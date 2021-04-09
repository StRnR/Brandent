package com.pixium.clinitick.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int taskId;

    private int clinicForId;
    private int dentistForId;

    private final UUID uuid;

    private final Long modifiedAt;
    private final Long time;

    private final String title;
    private final String state;

    private final int isDeleted;

    public Task(int clinicForId, int dentistForId, UUID uuid, Long modifiedAt, Long time
            , String title, String state, int isDeleted) {
        this.clinicForId = clinicForId;
        this.dentistForId = dentistForId;

        if (uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
        }

        if (modifiedAt == null) {
            this.modifiedAt = System.currentTimeMillis();
        } else {
            this.modifiedAt = modifiedAt;
        }

        this.time = time;
        this.title = title;
        this.state = state;
        this.isDeleted = isDeleted;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getClinicForId() {
        return clinicForId;
    }

    public int getDentistForId() {
        return dentistForId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public Long getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
