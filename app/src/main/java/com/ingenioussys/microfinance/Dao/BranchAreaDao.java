package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.BranchArea;

import java.util.List;

@Dao
public interface BranchAreaDao {

    @Query("SELECT * FROM manage_area")
    List<BranchArea> getAll();

    @Query("SELECT * FROM manage_area WHERE area_id IN (:area_id)")
    List<BranchArea> getSingle(int area_id);
    @Insert
    void insert(BranchArea area);

    @Delete
    void delete(BranchArea area);

    @Update
    void update(BranchArea area);
}
