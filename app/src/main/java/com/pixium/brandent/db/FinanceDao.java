package com.pixium.brandent.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface FinanceDao {
    @Insert
    void insert(Finance finance);

    @Update
    void update(Finance finance);

    @Delete
    void delete(Finance finance);
}
