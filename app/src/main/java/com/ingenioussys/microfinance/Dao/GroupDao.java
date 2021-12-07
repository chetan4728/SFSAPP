package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM manage_group ORDER BY group_id DESC")
    List<Group> getAll();

    @Query("SELECT * FROM manage_group WHERE created_by IN  (:employee_id) ORDER BY group_id DESC  ")
    List<Group> getAllGroupByUser(int employee_id);

    @Query("SELECT * FROM manage_center WHERE created_by IN  (:employee_id)  AND center_status = 1 ORDER BY center_id DESC " )
    List<Center> getAllByUser(int employee_id);

    @Query("SELECT * FROM manage_group WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id) AND group_id  IN (:group_id)  ")
    List<Group> get_single_record(int user_id, int branch_id, long group_id);

    @Query("SELECT * FROM manage_group WHERE bank_id IN (:bank_id) AND center_id  IN (:center_id) ORDER BY group_id DESC")
    List<Group> getAllByCenter(int bank_id, int center_id);

    @Query("SELECT * FROM manage_group WHERE  center_id  IN (:center_id) ORDER BY group_id DESC")
    List<Group> get_group_by_centers( int center_id);

    @Query("SELECT * FROM manage_group WHERE group_id IN (:group_id)")
    List<Group> get_group_name(int group_id);

    @Query("SELECT * FROM manage_group ")
    List<Group> get_by_group();

    @Insert
    long insert(Group group);

    @Delete
    void delete(Group group);

    @Update
    void update(Group group);

    @Query("DELETE FROM manage_group")
    public void deleteAllRecord();
}
