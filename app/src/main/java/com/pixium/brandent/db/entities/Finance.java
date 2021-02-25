package com.pixium.brandent.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "finances")
public class Finance {
    @PrimaryKey(autoGenerate = true)
    private int financeId;

    private final int dentistForId;
    private final int isDeleted;

    private final UUID uuid;

    private final long price;

    private final String title;
    private final String type;

    private final Long date;
    private final Long modifiedAt;

    public Finance(int dentistForId, UUID uuid, Long modifiedAt, Long date, long price, String title
            , String type, int isDeleted) {
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

        this.dentistForId = dentistForId;
        this.date = date;
        this.price = price;
        this.title = title;
        this.type = type;
        this.isDeleted = isDeleted;
    }

    public int getFinanceId() {
        return financeId;
    }

    public void setFinanceId(int financeId) {
        this.financeId = financeId;
    }

    public int getDentistForId() {
        return dentistForId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Long getDate() {
        return date;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public int getIsDeleted() {
        return isDeleted;
    }
}
