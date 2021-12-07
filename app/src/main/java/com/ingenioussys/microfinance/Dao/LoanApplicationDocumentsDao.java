package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.LoanApplicationDocument;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoanApplicationDocumentsDao {

    @Query("SELECT * FROM manage_loan_application_documents")
    List<LoanApplicationDocument> getAll();

    @Query("SELECT * FROM manage_loan_application_documents WHERE created_by IN (:user_id) AND  branch_id IN (:branch_id) AND loan_application_number IN (:loan_application_id)")
    List<LoanApplicationDocument> getSingle(int user_id, int branch_id, String loan_application_id);

    @Insert
    long insert(LoanApplicationDocument applicationDocument);

    @Delete
    void delete(LoanApplicationDocument applicationDocument);

    @Update(onConflict = REPLACE)
    void update(LoanApplicationDocument applicationDocument);


    
}
