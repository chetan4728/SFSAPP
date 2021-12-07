package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.LoanApplication;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoanApplicationDao {

    @Query("SELECT * FROM manage_loan_application")
    List<LoanApplication> getAll();

    @Query("SELECT * FROM manage_loan_application WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id) AND loan_application_number IN (:loan_application_number)")
    List<LoanApplication> getSingle(int user_id, int branch_id, String loan_application_number);


    @Query("SELECT * FROM manage_loan_application WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id)  AND  group_id IN(:group_id)" )
    List<LoanApplication> getAllByGroup(int user_id, int branch_id, int group_id);
    @Insert
    long insert(LoanApplication loanApplication);

    @Delete
    void delete(LoanApplication loanApplication);

    @Update(onConflict = REPLACE)
    void update(LoanApplication loanApplication);


    
}
