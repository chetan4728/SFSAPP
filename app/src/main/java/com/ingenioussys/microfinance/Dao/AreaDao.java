package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;

import java.util.List;
@Dao
public interface AreaDao {

    @Query("SELECT * FROM manage_assign_area")
    List<Area> getAll();

    @Query("SELECT * FROM manage_assign_area WHERE assign_area_id IN (:area_id)")
    List<Area> getSingle(int area_id);
    @Insert
    void insert(Area area);

    @Delete
    void delete(Area area);

    @Update
    void update(Area area);
}
