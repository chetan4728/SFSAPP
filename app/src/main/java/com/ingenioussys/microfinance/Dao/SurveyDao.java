package com.ingenioussys.microfinance.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Survey;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SurveyDao {


    @Query("SELECT * FROM manage_survey WHERE survey_created_by IN (:user_id) AND  branch_id IN (:branch_id) ")
    List<Survey> getAll(int user_id,int branch_id);

    @Query("SELECT * FROM manage_survey WHERE  branch_id  IN (:branch_id) ")
    List<Survey> GetAllSurveysForVerification(int branch_id);

    @Query("SELECT * FROM manage_survey WHERE   survey_uniqe_id IN (:survey_id) ")
    List<Survey> GetSingleSurveysForVerification(String survey_id);

    @Query("SELECT * FROM manage_survey WHERE  survey_uniqe_id  IN (:id) ")
    List<Survey> get_single_record( String id);

   // @Query("UPDATE manage_survey SET order_amount = :amount, price = :price WHERE order_id =:id")
    //void update(Float amount, Float price, int id);

    @Insert
    long insert(Survey survey);

    @Delete
    void delete(Survey survey);


    @Update(onConflict = REPLACE)
    void update(Survey survey);
}
