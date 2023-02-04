package com.ingenioussys.microfinance.ApiServices;
import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.LoanApplicationCashFlow;
import com.ingenioussys.microfinance.model.LoanApplicationCashTwoFlow;
import com.ingenioussys.microfinance.model.LoanApplicationDocument;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;
import com.ingenioussys.microfinance.utility.PrefManager;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIService {

    @FormUrlEncoded
    @POST("api/Android/loginSession")
    Call<Result> Login(@Field("employee_login_code") String employee_login_code, @Field("employee_login_password") String employee_login_password,
                       @Field("bank_app_key_code") String bank_app_key_code,@Field("employee_device")  String employee_device ,@Field("role_id")  int role_id);



    @FormUrlEncoded
    @POST("api/Android/SubmitAttendance")
    Call<Result> SubmitAttendance(@Field("user_id") String user_id, @Field("branch_id") String branch_id,
             @Field("bank_id") String bank_id, @Field("latitude") double latitude,@Field("longitude") double longitude,@Field("location_address") String location_address
                                );
    @FormUrlEncoded
    @POST("api/Android/CheckAttendance")
    Call<Result> CheckAttendance(@Field("user_id") String user_id, @Field("branch_id") String branch_id,
                                 @Field("bank_id") String bank_id);

    @FormUrlEncoded
    @POST("api/Android/createSurveyUniqeNo")
    Call<Result> createSurveyUniqeNo(@Field("bank_id") String bank_id,
                                     @Field("assign_area_id") int assign_area_id,
                                     @Field("branch_id") String branch_id,
                                     @Field("area_id") String area_id,
                                     @Field("bank_prefix") String bank_prefix);


    @FormUrlEncoded
    @POST("api/Android/createLoanApplicationNo")
    Call<Result> createLoanApplicationNo(@Field("bank_id") String bank_id,
                                         @Field("area_id") int area_id,
                                         @Field("branch_id") String branch_id,
                                         @Field("center_id") int center_id,
                                         @Field("group_id") int group_id,
                                         @Field("bank_prefix") String bank_prefix);

    @FormUrlEncoded
    @POST("api/Android/GetArea")
    Call<Result> GetArea(@Field("bank_id") String bank_id, @Field("user_id") String employee_id);

    @FormUrlEncoded
    @POST("api/Android/GetBranchArea")
    Call<Result> GetBranchArea(@Field("bank_id") String bank_id);

    @FormUrlEncoded
    @POST("api/Android/LoadCenterTable")
    Call<Result> LoadCenterTable(@Field("bank_id") String bank_id,@Field("branch_id") String branch_id,@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("api/Android/LoadGroupsTable")
    Call<Result> LoadGroupsTable(@Field("bank_id") String bank_id,@Field("branch_id") String branch_id,@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/Android/LoadCenterGroupsTable")
    Call<Result> LoadCenterGroupsTable(@Field("bank_id") String bank_id,@Field("branch_id") String branch_id,@Field("center_id") String center_id,@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/Android/LoadGroupsTableAll")
    Call<Result> LoadGroupsTableAll(@Field("bank_id") String bank_id);

    @FormUrlEncoded
    @POST("api/Android/SubmitAttendanceLogout")
    Call<Result> SubmitAttendanceLogout(@Field("user_id") String user_id,@Field("login_time") String login_time,@Field("created_at") String created_at);


    @POST("api/Android/SubmitSurvey")
    Call<Result> SubmitSurvey(@Body List<Survey> survey);


    @FormUrlEncoded
    @POST("api/Android/LoadLoanApplications")
    Call<Result> LoadLoanApplications(@Field("bank_id") String bank_id,@Field("employee_id") String employee_id);

    @POST("api/Android/SubmitLoanApplication")
    Call<Result> SubmitLoanApplication(@Body List<LoanApplication> survey);


    @POST("api/Android/SubmitSurveyRow")
    Call<Result> SubmitSurveyRow(@Body List<Survey> survey);

    @POST("api/Android/UpdateSurveyRow")
    Call<Result> UpdateSurveyRow(@Body List<Survey> survey);

    @POST("api/Android/UpdatecashflowRow")
    Call<Result> UpdatecashflowRow(@Body List<LoanApplicationCashFlow> loanApplicationCashFlows);

    @POST("api/Android/UpdatecashflowTwoRow")
    Call<Result> UpdatecashflowTwoRow(@Body List<LoanApplicationCashTwoFlow> loanApplicationCashTwoFlows);

    @POST("api/Android/UpdateLoandApplicationRow")
    Call<Result> UpdateLoandApplicationRow(@Body List<LoanApplication> loanApplications);

    //@Multipart
    @POST("api/Android/UpdateLoanApplicationDocument")
    Call<Result> UpdateLoanApplicationDocument(@Body List<LoanApplicationDocument> loanApplicationDocuments);

    @POST("api/Android/SubmitCenterRow")
    Call<Result> SubmitCenterRow(@Body List<Center> center);

    @POST("api/Android/SubmitGroupRow")
    Call<Result> SubmitGroupRow(@Body List<Group> group);

    @POST("api/Android/SubmitCGTRow")
    Call<Result> SubmitCGTRow(@Body List<CGT> group);

    @Multipart
    @POST("api/Android/UploadDocuments")
    Call<Result> UploadDocuments(@PartMap Map<String, RequestBody> map);

    @Multipart
        @POST("api/Android/SubmitCGTImage")
    Call<Result> SubmitCGTImage(@PartMap Map<String, RequestBody> map);

    @GET("api/Android/GetRole")
    Call<Result> GetRole();

    @FormUrlEncoded
    @POST("api/Android/getSurveys")
    Call<Result> getSurveys(@Field("bank_id") String bank_id);

    @GET("api/Android/manage_process")
    Call<Result> manage_process();

    @GET("api/Android/manage_day")
    Call<Result> manage_day();

    @FormUrlEncoded
    @POST("api/Android/LoadBranchData")
    Call<Result> GetBranch(@Field("bank_id") String bank_id);


    @FormUrlEncoded
    @POST("api/Android/LoadCGTData")
    Call<Result> LoadCGTData(@Field("bank_id") String bank_id,@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/Android/updateCenterverification")
    Call<Result> updateCenterverification(@Field("branch_id") int branch_id,@Field("center_id") int center_id,@Field("center_status") int center_status);

    @FormUrlEncoded
    @POST("api/Android/updateGroupVerification")
    Call<Result> updateGroupVerification(@Field("branch_id") int branch_id,@Field("center_id") int center_id,@Field("group_id") int group_id,@Field("GroupStatus") int GroupStatus);

    @FormUrlEncoded
    @POST("api/Android/get_emi_collectiondata")
    Call<Result> get_emi_collectiondata(@Field("bank_id") String bank_id, @Field("branch_id") String branch_id, @Field("page") int page, @Field("limit") int limit , @Field("employee_id") int employee_id, @Field("group_name") String group_name);


    @FormUrlEncoded
    @POST("api/Android/get_loan_application_list")
    Call<Result> get_loan_application_list(@Field("bank_id") String bank_id, @Field("branch_id") String branch_id, @Field("page") int page, @Field("limit") int limit, @Field("employee_id") int employee_id, @Field("group_name") String group_name);
    @FormUrlEncoded
    @POST("api/Android/get_loan_emi_details")
    Call<Result> get_loan_emi_details(@Field("laon_application_no") String laon_application_no,@Field("loan_distribution_emi_id") String loan_distribution_emi_id);


    @FormUrlEncoded
    @POST("api/Android/get_loan_emi_employee_details")
    Call<Result> get_loan_emi_employee_details(@Field("laon_application_no") String laon_application_no);

    @FormUrlEncoded
    @POST("api/Android/get_loan_details")
    Call<Result> get_loan_details(@Field("laon_application_no") String laon_application_no);

    @FormUrlEncoded
    @POST("api/Android/approve_loan_application_filed_manager")
    Call<Result> approve_loan_application_filed_manager(@Field("loan_application_number") String loan_application_number, @Field("approved_by") String approved_by, @Field("approve_status")  int approve_status);


    @FormUrlEncoded
    @POST("api/Android/update_emi_amount")
    Call<Result> update_emi_amount(@Field("laon_application_no") String laon_application_no,@Field("loan_distribution_emi_id") String loan_distribution_emi_id,@Field("paid_amount") String paid_amount,@Field("get_saving_amount") String get_saving_amount);

    @FormUrlEncoded
    @POST("api/Android/get_group_members")
    Call<Result> get_group_members(@Field("center_id") String center_id,@Field("group_id") String group_id,@Field("bank_id") String bank_id,@Field("branch_id") String branch_id);

    @FormUrlEncoded
    @POST("api/Android/get_loan_emi_listing")
    Call<Result> get_loan_emi_listing(@Field("laon_application_no") String laon_application_no);
}

