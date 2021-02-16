package com.pixium.brandent.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "finances")
public class Finance {
    @PrimaryKey(autoGenerate = true)
    private int financeId;

    private int dentistForId;

    private UUID uuid;

    private int price;

    private String title;
    private String type;

    private Long date;
    private Long modifiedAt;

    public Finance(int dentistForId, UUID uuid, Long modifiedAt, Long date, int price, String title, String type) {
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
    }

    public void setFinanceId(int financeId) {
        this.financeId = financeId;
    }

    public int getFinanceId() {
        return financeId;
    }

    public int getDentistForId() {
        return dentistForId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPrice() {
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
}
