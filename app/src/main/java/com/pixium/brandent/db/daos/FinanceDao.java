package com.pixium.brandent.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.brandent.db.entities.Finance;

import java.util.List;

@Dao
public interface FinanceDao {
    @Insert
    void insert(Finance finance);

    @Update
    void update(Finance finance);

    @Delete
    void delete(Finance finance);

    @Query("SELECT price FROM finances WHERE date BETWEEN :start AND :end AND type = :argType " +
            "ORDER BY date ASC")
    public List<Integer> getFinanceSumByDateAndType(long start, long end, String argType);
}
