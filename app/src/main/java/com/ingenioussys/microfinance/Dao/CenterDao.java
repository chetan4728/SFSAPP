package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.Survey;

import java.util.List;
@Dao
public interface CenterDao {

    @Query("SELECT * FROM manage_center")
    List<Center> getAll();

    @Query("SELECT * FROM manage_center WHERE created_by IN  (:employee_id) ORDER BY center_id DESC " )
    List<Center> getAllByUser(int employee_id);


    @Query("SELECT * FROM manage_center WHERE area_id IN  (:area_id)")
    List<Center> getCenterByArea(int area_id);

    @Query("SELECT * FROM manage_center WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id) AND center_id  IN (:center_id)  ")
    List<Center> get_single_record(int user_id, int branch_id, long center_id);


    @Query("SELECT * FROM manage_center WHERE branch_id IN  (:branch_id) ORDER BY center_id DESC")
    List<Center> getCenterByBranch(int branch_id);

    @Query("DELETE FROM manage_center")
    public void deleteAllRecord();

    @Query("SELECT * FROM manage_center WHERE center_id IN (:center_id)")
    List<Center> get_center_name(int center_id);
    @Insert
    long insert(Center center);

    @Delete
    void delete(Center center);

    @Update
    void update(Center center);
}
