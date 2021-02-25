package com.pixium.brandent.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pixium.brandent.db.entities.Finance;

import java.util.List;
import java.util.UUID;

@Dao
public interface FinanceDao {
    @Insert
    void insert(Finance finance);

    @Update
    void update(Finance finance);

    @Delete
    void delete(Finance finance);

    @Query("SELECT * FROM finances WHERE uuid=:arg AND dentistForId=:activeUserId")
    Finance getByUuid(UUID arg, int activeUserId);

    @Query("SELECT * FROM finances WHERE modifiedAt > :updatedAt AND dentistForId=:activeUserId")
    Finance[] getUnSynced(long updatedAt, int activeUserId);

    @Query("SELECT price FROM finances WHERE date BETWEEN :start AND :end AND type = :argType " +
            "AND dentistForId=:activeUserId ORDER BY date ASC")
    List<Integer> getFinanceSumByDateAndType(long start, long end, String argType
            , int activeUserId);
}
