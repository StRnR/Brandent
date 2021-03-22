package com.pixium.clinitick.db.converters;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class TimestampConverters {
    @TypeConverter
    public static Timestamp fromLong(Long time) {
        return time == null ? null : new Timestamp(time);
    }

    @TypeConverter
    public static Long timestampToLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }
}
