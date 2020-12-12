package com.pixium.brandent.db;

import androidx.room.TypeConverter;

import java.util.UUID;

public class UUIDConverters {
    @TypeConverter
    public static UUID fromMyString(String string) {
        return string == null ? null : UUID.fromString(string);
    }

    @TypeConverter
    public static String uuidToString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }
}
