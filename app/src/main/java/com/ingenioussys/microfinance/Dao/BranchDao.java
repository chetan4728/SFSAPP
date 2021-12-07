package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Branch;

import java.util.List;

@Dao
public interface BranchDao {

    @Query("SELECT * FROM manage_branch")
    List<Branch> getAll();

    @Insert
    void insert(Branch branch);

    @Delete
    void delete(Branch branch);

    @Update
    void update(Branch branch);
}
