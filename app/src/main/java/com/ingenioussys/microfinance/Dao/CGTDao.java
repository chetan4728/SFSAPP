package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Survey;

import java.util.List;
@Dao
public interface CGTDao {

    @Query("SELECT * FROM manage_cgt")
    List<CGT> getAll();

    @Query("DELETE FROM manage_cgt")
    void deleteALL();

    @Query("SELECT * FROM manage_cgt WHERE cgt_id IN  (:cgt_id)")
    List<CGT> getLastDay(int cgt_id);

    @Query("SELECT * FROM manage_cgt WHERE cgt_added_by IN (:user_id) AND  branch_id IN (:branch_id) AND cgt_id  IN (:cgt_id)  ")
    List<CGT> get_single_record(int user_id, int branch_id, long cgt_id);
    @Insert
    long insert(CGT cgt);

    @Delete
    void delete(CGT cgt);

    @Update
    void update(CGT cgt);
}
