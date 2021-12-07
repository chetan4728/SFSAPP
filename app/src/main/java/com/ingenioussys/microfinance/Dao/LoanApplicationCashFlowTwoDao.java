package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.LoanApplicationCashTwoFlow;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoanApplicationCashFlowTwoDao {

    @Query("SELECT * FROM manage_loan_application_cash_flow_two")
    List<LoanApplicationCashTwoFlow> getAll();

    @Query("SELECT * FROM manage_loan_application_cash_flow_two WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id) AND loan_application_number IN (:loan_application_id)")
    List<LoanApplicationCashTwoFlow> getSingle(int user_id, int branch_id, String loan_application_id);

    @Insert
    long insert(LoanApplicationCashTwoFlow applicationCashTwoFlow);

    @Delete
    void delete(LoanApplicationCashTwoFlow applicationCashTwoFlow);

    @Update(onConflict = REPLACE)
    void update(LoanApplicationCashTwoFlow applicationCashTwoFlow);


    
}
