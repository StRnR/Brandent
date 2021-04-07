package com.pixium.clinitick.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.clinitick.db.entities.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task WHERE modifiedAt > :updatedAt AND dentistForId=:activeUserId")
    Task[] getUnSynced(long updatedAt, int activeUserId);

    @Query("SELECT * FROM task WHERE time BETWEEN :start AND :end " +
            "AND dentistForId=:activeUserId ORDER BY time ASC")
    List<Task> getByDate(long start, long end, int activeUserId);

    @Query("SELECT * FROM task WHERE taskId=:arg AND dentistForId=:activeUserId")
    Task getById(int arg, int activeUserId);

    @Query("SELECT * FROM task WHERE uuid=:arg AND dentistForId=:activeUserId")
    Task getByUuid(UUID arg, int activeUserId);
}
