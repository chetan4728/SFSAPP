package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Day;

import java.util.List;

@Dao
public interface DayDao {
    @Query("SELECT * FROM manage_day LIMIT :limit OFFSET :offset")
    List<Day> getAll(int limit,int offset);



    @Insert
    void insert(Day day);

    @Delete
    void delete(Day day);

    @Update
    void update(Day day);
}
