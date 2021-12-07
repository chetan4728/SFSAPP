package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.LoanApplicationCashFlow;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoanApplicationCashFlowDao {

    @Query("SELECT * FROM manage_loan_application_cash_flow")
    List<LoanApplicationCashFlow> getAll();

    @Query("SELECT * FROM manage_loan_application_cash_flow WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id) AND loan_application_number IN (:loan_application_no)")
    List<LoanApplicationCashFlow> getSingle(int user_id, int branch_id, String loan_application_no);

    @Insert
    long insert(LoanApplicationCashFlow applicationCashFlow);

    @Delete
    void delete(LoanApplicationCashFlow applicationCashFlow);

    @Update(onConflict = REPLACE)
    void update(LoanApplicationCashFlow applicationCashFlow);


    
}
