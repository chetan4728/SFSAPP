package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.CGTProcess;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Day;

import java.util.List;

@Dao
public interface CGTProcessDao {



    @Query("SELECT * FROM manage_process LIMIT :limit OFFSET :offset")
    List<CGTProcess> getAll(int limit, int offset);

    @Insert
    void insert(CGTProcess cgt);

    @Delete
    void delete(CGTProcess cgt);

    @Update
    void update(CGTProcess cgt);
}
