package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Role;

import java.util.List;

@Dao
public interface RoleDao {

    @Query("SELECT * FROM manage_role")
    List<Role> getAll();

    @Insert
    void insert(Role role);

    @Delete
    void delete(Role role);

    @Update
    void update(Role role);
}
