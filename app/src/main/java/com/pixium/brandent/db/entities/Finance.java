package com.pixium.brandent.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "finances")
public class Finance {
    @PrimaryKey(autoGenerate = true)
    private int financeId;

    private UUID uuid;

    @ColumnInfo(name = "price")
    private int price;

    private String title;
    private String type;

    private Long date;
    private Long modifiedAt;

    public Finance(UUID uuid, Long modifiedAt, Long date, int price, String title, String type) {
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
