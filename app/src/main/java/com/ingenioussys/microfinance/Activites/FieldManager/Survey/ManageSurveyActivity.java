package com.ingenioussys.microfinance.Activites.FieldManager.Survey;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class ManageSurveyActivity extends AppCompatActivity {
    HintSpinner bus_service ,auto_service,police_station,market,primary_health_center,primary_school,quality_of_roads;
    List<String> categories,RoadService;
    ArrayList<Area> areas;
    GPS_Service gps;
    EditText created_by,created_date,enter_total_household,enter_minority_household,enter_sc_household,enter_obc_household,enter_st_household;
    EditText enter_area_acres,enter_water_reservolr,enter_rain_fed,enter_forest_land,enter_irrigated_area,enter_crop_name,enter_under_cultivation;
    EditText enter_per_acre_yield,enter_crop_name_second,enter_per_acre_yield_second,enter_crop_name_third,enter_under_cultivation_third,enter_per_acre_yield_third;
    EditText enter_crop_name_fourth,enter_under_cultivation_fourth,enter_per_acre_yield_fourth,enter_number_of_hotels,enter_number_of_utensil_shops,enter_number_of_tea_shops;
    EditText enter_number_of_agri_shops,enter_number_of_kirana_shops,enter_number_of_agree_proce_unit,enter_number_of_tailoring_shops,enter_number_of_pan_shops,enter_number_of_cycle_shops;
    EditText enter_number_of_other_shops,enter_number_of_repair_service_shops,number_of_school,enter_dairy_society_number,enter_dairy_society_client,enter_farmers_club_number;
    EditText enter_farmers_club_client,enter_shgs_number,enter_shgs_client,enter_co_operative_number,enter_co_operative_client,enter_co_operative_branch_number;
    EditText area_name,enter_co_operative_branch_client,enter_grameem_branch_number,enter_grameem_branch_client,enter_comercial_branch_number,enter_comercial_branch_client,enter_mfi_number_1;
        EditText enter_mfi_client_1,enter_mfi_name_1,enter_mfi_name_2,enter_mfi_number_2,enter_mfi_client_2,enter_mfi_name_3,enter_mfi_number_3;
    EditText enter_mfi_client_3,enter_mfi_name_4,enter_mfi_number_4,enter_mfi_client_4,mbb_name,enter_under_cultivation_second;
    EditText survey_title;
    PrefManager prefManager;
    Button submit;
    Survey survey;
    int area_id = 0;
    ProgressDialog progressDialog;
    List<Survey> SurveyList;
    List<Survey> List;
    int approved_status = 0;
    public RadioGroup approval;
    public RadioButton approved,disapproved;
    String survey_uniqe_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_verfication_survey_form);
        getSupportActionBar().setTitle("Survey Form");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager =  new PrefManager(this);
        gps =  new GPS_Service(ManageSurveyActivity.this);
        bus_service =  findViewById(R.id.bus_service);
        survey_title =  findViewById(R.id.survey_title);
        auto_service =  findViewById(R.id.auto_service);
        police_station =  findViewById(R.id.police_station);
        police_station =  findViewById(R.id.police_station);
        created_date =  findViewById(R.id.created_date);
        approval =  findViewById(R.id.approval);
        approved =  findViewById(R.id.approved);
        disapproved =  findViewById(R.id.disapproved);
        enter_total_household =  findViewById(R.id.enter_total_household);
        enter_minority_household =  findViewById(R.id.enter_minority_household);
        enter_sc_household =  findViewById(R.id.enter_sc_household);
        enter_obc_household =  findViewById(R.id.enter_obc_household);
        enter_st_household =  findViewById(R.id.enter_st_household);
        enter_area_acres =  findViewById(R.id.enter_area_acres);
        enter_water_reservolr =  findViewById(R.id.enter_water_reservolr);
        enter_rain_fed =  findViewById(R.id.enter_rain_fed);
        enter_forest_land =  findViewById(R.id.enter_forest_land);
        enter_irrigated_area =  findViewById(R.id.enter_irrigated_area);
        enter_crop_name =  findViewById(R.id.enter_crop_name);
        enter_under_cultivation =  findViewById(R.id.enter_under_cultivation);
        enter_per_acre_yield =  findViewById(R.id.enter_per_acre_yield);
        enter_crop_name_second =  findViewById(R.id.enter_crop_name_second);
        enter_per_acre_yield_second =  findViewById(R.id.enter_per_acre_yield_second);
        enter_crop_name_third =  findViewById(R.id.enter_crop_name_third);
        enter_under_cultivation_third =  findViewById(R.id.enter_under_cultivation_third);
        enter_per_acre_yield_third =  findViewById(R.id.enter_per_acre_yield_third);
        enter_crop_name_fourth =  findViewById(R.id.enter_crop_name_fourth);
        enter_under_cultivation_fourth =  findViewById(R.id.enter_under_cultivation_fourth);
        enter_per_acre_yield_fourth =  findViewById(R.id.enter_per_acre_yield_fourth);
        enter_number_of_hotels =  findViewById(R.id.enter_number_of_hotels);
        enter_number_of_utensil_shops =  findViewById(R.id.enter_number_of_utensil_shops);
        enter_number_of_tea_shops =  findViewById(R.id.enter_number_of_tea_shops);
        enter_number_of_agri_shops =  findViewById(R.id.enter_number_of_agri_shops);
        enter_number_of_kirana_shops =  findViewById(R.id.enter_number_of_kirana_shops);
        enter_number_of_agree_proce_unit =  findViewById(R.id.enter_number_of_agree_proce_unit);
        enter_number_of_tailoring_shops =  findViewById(R.id.enter_number_of_tailoring_shops);
        enter_number_of_cycle_shops =  findViewById(R.id.enter_number_of_cycle_shops);
        enter_number_of_pan_shops =  findViewById(R.id.enter_number_of_pan_shops);
        enter_number_of_other_shops =  findViewById(R.id.enter_number_of_other_shops);
        enter_number_of_repair_service_shops =  findViewById(R.id.enter_number_of_repair_service_shops);
        number_of_school =  findViewById(R.id.number_of_school);
        enter_dairy_society_number =  findViewById(R.id.enter_dairy_society_number);
        enter_dairy_society_client =  findViewById(R.id.enter_dairy_society_client);
        enter_farmers_club_number =  findViewById(R.id.enter_farmers_club_number);
        enter_farmers_club_client =  findViewById(R.id.enter_farmers_club_client);
        enter_shgs_number =  findViewById(R.id.enter_shgs_number);
        enter_co_operative_number =  findViewById(R.id.enter_co_operative_number);
        enter_shgs_client =  findViewById(R.id.enter_shgs_client);
        enter_co_operative_client =  findViewById(R.id.enter_co_operative_client);
        enter_co_operative_branch_number =  findViewById(R.id.enter_co_operative_branch_number);
        enter_co_operative_branch_client =  findViewById(R.id.enter_co_operative_branch_client);
        enter_grameem_branch_number =  findViewById(R.id.enter_grameem_branch_number);
        enter_grameem_branch_client =  findViewById(R.id.enter_grameem_branch_client);
        enter_comercial_branch_number =  findViewById(R.id.enter_comercial_branch_number);
        enter_mfi_number_1 =  findViewById(R.id.enter_mfi_number_1);
        enter_comercial_branch_client =  findViewById(R.id.enter_comercial_branch_client);
        enter_mfi_client_1 =  findViewById(R.id.enter_mfi_client_1);
        enter_mfi_name_1 =  findViewById(R.id.enter_mfi_name_1);
        enter_mfi_name_2 =  findViewById(R.id.enter_mfi_name_2);
        enter_mfi_number_2 =  findViewById(R.id.enter_mfi_number_2);
        enter_mfi_client_2 =  findViewById(R.id.enter_mfi_client_2);
        enter_mfi_name_3 =  findViewById(R.id.enter_mfi_name_3);
        enter_mfi_number_3 =  findViewById(R.id.enter_mfi_number_3);
        enter_mfi_client_3 =  findViewById(R.id.enter_mfi_client_3);
        enter_mfi_name_4 =  findViewById(R.id.enter_mfi_name_4);
        enter_mfi_number_4 =  findViewById(R.id.enter_mfi_number_4);
        enter_mfi_client_4 =  findViewById(R.id.enter_mfi_client_4);
        enter_under_cultivation_second =  findViewById(R.id.enter_under_cultivation_second);

        mbb_name =  findViewById(R.id.mbb_name);
        submit =  findViewById(R.id.submit);

        created_by =  findViewById(R.id.created_by);
        market =  findViewById(R.id.market);
        primary_health_center =  findViewById(R.id.primary_health_center);
        primary_school =  findViewById(R.id.primary_school);
        quality_of_roads =  findViewById(R.id.quality_of_roads);
        area_name=  findViewById(R.id.area_name);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Submitting Data...");

       // Toast.makeText(this, ""+getIntent().getStringExtra("survey_id"), Toast.LENGTH_SHORT).show();

        categories = new ArrayList<String>();
        categories.add("Yes");
        categories.add("No");

        RoadService = new ArrayList<String>();
        RoadService.add("Good");
        RoadService.add("Bad");


        bus_service.setAdapter(new HintSpinnerAdapter<String>(this, categories, "Bus Service"));
        auto_service.setAdapter(new HintSpinnerAdapter<String>(this, categories, "Auto Service"));
        police_station.setAdapter(new HintSpinnerAdapter<String>(this, categories, "Police Station"));
        market.setAdapter(new HintSpinnerAdapter<String>(this, categories, "Market"));
        primary_health_center.setAdapter(new HintSpinnerAdapter<String>(this, categories, "Primary Health Center"));
        primary_school.setAdapter(new HintSpinnerAdapter<String>(this, categories, "Primary School"));
        quality_of_roads.setAdapter(new HintSpinnerAdapter<String>(this, RoadService, "Quality of Roads"));

        AsyncTask.execute(() -> {

            String survey_id = getIntent().getStringExtra("survey_id");
//            List =   AppDatabase.getDatabase(getApplicationContext()).surveyDao().GetSingleSurveysForVerification(survey_id);
            area_id = List.get(0).getArea_id();
            survey_uniqe_id = List.get(0).getSurvey_uniqe_id();
            area_name.setText(List.get(0).getSurvey_area_name());
            survey_title.setText(List.get(0).getSurvey_title());
            mbb_name.setText(List.get(0).getMbb_name());
            created_date.setText(List.get(0).getSurvey_created());
            enter_total_household.setText(List.get(0).getTotal_household());
            enter_minority_household.setText(List.get(0).getMinority_household());
            enter_sc_household.setText(List.get(0).getSc_household());
            enter_st_household.setText(List.get(0).getSt_household());
            enter_obc_household.setText(List.get(0).getObc_household());
            enter_area_acres.setText(List.get(0).getArea_acres());
            enter_water_reservolr.setText(List.get(0).getWater_reservolr());
            enter_rain_fed.setText(List.get(0).getRain_fed());
            enter_minority_household.setText(List.get(0).getMinority_household());
            enter_forest_land.setText(List.get(0).getForest_land());
            enter_irrigated_area.setText(List.get(0).getIrrigated_area());
            enter_crop_name.setText(List.get(0).getCrop_name());
            enter_under_cultivation.setText(List.get(0).getUnder_cultivation());
            enter_per_acre_yield.setText(List.get(0).getPer_acre_yield());
            enter_crop_name_second.setText(List.get(0).getCrop_name_second());
            enter_under_cultivation_second.setText(List.get(0).getUnder_cultivation_second());
            enter_per_acre_yield_second.setText(List.get(0).getPer_acre_yield_second());
            enter_crop_name_third.setText(List.get(0).getCrop_name_third());
            enter_under_cultivation_third.setText(List.get(0).getUnder_cultivation_third());
            enter_per_acre_yield_third.setText(List.get(0).getPer_acre_yield_third());
            enter_crop_name_fourth.setText(List.get(0).getCrop_name_fourth());
            enter_under_cultivation_fourth.setText(List.get(0).getUnder_cultivation_fourth());
            enter_per_acre_yield_fourth.setText(List.get(0).getPer_acre_yield_fourth());
            enter_number_of_hotels.setText(List.get(0).getNumber_of_hotels());
            enter_number_of_utensil_shops.setText(List.get(0).getNumber_of_utensil_shops());
            enter_number_of_tea_shops.setText(List.get(0).getNumber_of_tea_shops());
            enter_number_of_agri_shops.setText(List.get(0).getNumber_of_agri_shops());
            enter_number_of_kirana_shops.setText(List.get(0).getNumber_of_kirana_shops());
            enter_number_of_agree_proce_unit.setText(List.get(0).getNumber_of_agree_proce_unit());
            enter_number_of_tailoring_shops.setText(List.get(0).getNumber_of_tailoring_shops());
            enter_number_of_pan_shops.setText(List.get(0).getNumber_of_pan_shops());
            enter_number_of_cycle_shops.setText(List.get(0).getNumber_of_cycle_shops());
            enter_number_of_other_shops.setText(List.get(0).getNumber_of_other_shops());
            enter_number_of_repair_service_shops.setText(List.get(0).getNumber_of_repair_service_shops());
            enter_dairy_society_number.setText(List.get(0).getDairy_society_number());
            enter_dairy_society_client.setText(List.get(0).getDairy_society_client());
            enter_farmers_club_number.setText(List.get(0).getFarmers_club_number());
            enter_farmers_club_client.setText(List.get(0).getFarmers_club_client());
            enter_shgs_number.setText(List.get(0).getShgs_number());
            enter_shgs_client.setText(List.get(0).getShgs_client());
            enter_co_operative_branch_number.setText(List.get(0).getCo_operative_branch_number());
            enter_co_operative_branch_client.setText(List.get(0).getCo_operative_branch_client());
            enter_co_operative_client.setText(List.get(0).getCo_operative_client());
            enter_co_operative_number.setText(List.get(0).getCo_operative_number());
            enter_grameem_branch_client.setText(List.get(0).getGrameem_branch_client());
            enter_grameem_branch_number.setText(List.get(0).getGrameem_branch_number());
            enter_comercial_branch_client.setText(List.get(0).getComercial_branch_client());
            enter_comercial_branch_number.setText(List.get(0).getComercial_branch_number());
            enter_mfi_client_1.setText(List.get(0).getMfi_client_1());
            enter_mfi_client_2.setText(List.get(0).getMfi_client_2());
            enter_mfi_client_3.setText(List.get(0).getMfi_client_3());
            enter_mfi_client_4.setText(List.get(0).getMfi_client_4());
            enter_mfi_name_1.setText(List.get(0).getMfi_name_1());
            enter_mfi_name_2.setText(List.get(0).getMfi_name_2());
            enter_mfi_name_3.setText(List.get(0).getMfi_name_3());
            enter_mfi_name_4.setText(List.get(0).getMfi_name_4());
            enter_mfi_number_1.setText(List.get(0).getMfi_number_1());
            enter_mfi_number_2.setText(List.get(0).getMfi_number_2());
            enter_mfi_number_3.setText(List.get(0).getMfi_number_3());
            enter_mfi_number_4.setText(List.get(0).getMfi_number_4());



        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(approved.isChecked())
                {
                    approved_status = 1;
                }
                else  if(disapproved.isChecked())
                {
                    approved_status  = 0;
                }
                else
                {
                    approved_status =0;
                }
             //   Toast.makeText(getApplicationContext(), ""+approved_status, Toast.LENGTH_SHORT).show();

                    SurveyList =  new ArrayList<>();
                    survey =  new Survey();
                    survey.setSurver_verfication_status(approved_status);
                    survey.setSurvey_uniqe_id(List.get(0).getSurvey_uniqe_id());
                    survey.setSuvery_verfied_by(Integer.parseInt(prefManager.getString("employee_id")));
                   /* survey.setBranch_id(Integer.parseInt(getIntent().getStringExtra("branch_id")));
                    survey.setSurvey_id(0);
                    survey.setId(List.get(0).getId());
                    survey.setSurvey_uniqe_id(survey_uniqe_id);
                    survey.setArea_id(area_id);
                    survey.setMbb_name(mbb_name.getText().toString());
                    survey.setSurvey_created(created_date.getText().toString());
                    survey.setTotal_household(enter_total_household.getText().toString());
                    survey.setMinority_household(enter_minority_household.getText().toString());
                    survey.setSc_household(enter_sc_household.getText().toString());
                    survey.setObc_household(enter_obc_household.getText().toString());
                    survey.setSt_household(enter_st_household.getText().toString());
                    survey.setArea_acres(enter_area_acres.getText().toString());
                    survey.setWater_reservolr(enter_water_reservolr.getText().toString());
                    survey.setRain_fed(enter_rain_fed.getText().toString());
                    survey.setForest_land(enter_forest_land.getText().toString());
                    survey.setIrrigated_area(enter_irrigated_area.getText().toString());
                    survey.setCrop_name(enter_crop_name.getText().toString());
                    survey.setUnder_cultivation(enter_under_cultivation.getText().toString());
                    survey.setPer_acre_yield(enter_per_acre_yield.getText().toString());
                    survey.setCrop_name_second(enter_crop_name_second.getText().toString());
                    survey.setUnder_cultivation_second(enter_under_cultivation_second.getText().toString());
                    survey.setPer_acre_yield_second(enter_per_acre_yield_second.getText().toString());
                    survey.setCrop_name_third(enter_crop_name_third.getText().toString());
                    survey.setUnder_cultivation_third(enter_under_cultivation_third.getText().toString());
                    survey.setPer_acre_yield_third(enter_per_acre_yield_third.getText().toString());
                    survey.setCrop_name_fourth(enter_crop_name_fourth.getText().toString());
                    survey.setUnder_cultivation_fourth(enter_under_cultivation_fourth.getText().toString());
                    survey.setPer_acre_yield_fourth(enter_per_acre_yield_fourth.getText().toString());
                    survey.setNumber_of_hotels(enter_number_of_hotels.getText().toString());
                    survey.setNumber_of_utensil_shops(enter_number_of_utensil_shops.getText().toString());
                    survey.setNumber_of_tea_shops(enter_number_of_tea_shops.getText().toString());
                    survey.setNumber_of_agri_shops(enter_number_of_agri_shops.getText().toString());
                    survey.setNumber_of_kirana_shops(enter_number_of_kirana_shops.getText().toString());
                    survey.setNumber_of_tailoring_shops(enter_number_of_tailoring_shops.getText().toString());
                    survey.setNumber_of_agree_proce_unit(enter_number_of_agree_proce_unit.getText().toString());
                    survey.setNumber_of_pan_shops(enter_number_of_pan_shops.getText().toString());
                    survey.setNumber_of_cycle_shops(enter_number_of_cycle_shops.getText().toString());
                    survey.setNumber_of_other_shops(enter_number_of_other_shops.getText().toString());
                    survey.setNumber_of_repair_service_shops(enter_number_of_repair_service_shops.getText().toString());
                    survey.setDairy_society_number(enter_dairy_society_number.getText().toString());
                    survey.setDairy_society_client(enter_dairy_society_client.getText().toString());
                    survey.setFarmers_club_number(enter_farmers_club_number.getText().toString());
                    survey.setFarmers_club_client(enter_farmers_club_client.getText().toString());
                    survey.setShgs_client(enter_shgs_client.getText().toString());
                    survey.setShgs_number(enter_shgs_number.getText().toString());
                    survey.setCo_operative_number(enter_co_operative_number.getText().toString());
                    survey.setCo_operative_client(enter_co_operative_client.getText().toString());
                    survey.setCo_operative_branch_number(enter_co_operative_branch_number.getText().toString());
                    survey.setCo_operative_branch_client(enter_co_operative_branch_client.getText().toString());
                    survey.setGrameem_branch_client(enter_grameem_branch_client.getText().toString());
                    survey.setGrameem_branch_number(enter_grameem_branch_number.getText().toString());
                    survey.setComercial_branch_client(enter_comercial_branch_client.getText().toString());
                    survey.setComercial_branch_number(enter_comercial_branch_number.getText().toString());
                    survey.setMfi_client_1(enter_mfi_client_1.getText().toString());
                    survey.setMfi_client_2(enter_mfi_client_2.getText().toString());
                    survey.setMfi_client_3(enter_mfi_client_3.getText().toString());
                    survey.setMfi_client_4(enter_mfi_client_4.getText().toString());
                    survey.setMfi_name_1(enter_mfi_name_1.getText().toString());
                    survey.setMfi_name_2(enter_mfi_name_2.getText().toString());
                    survey.setMfi_name_3(enter_mfi_name_3.getText().toString());
                    survey.setMfi_name_4(enter_mfi_name_4.getText().toString());

                    survey.setBus_service(bus_service.getSelectedItem().toString());
                    survey.setAuto_service(auto_service.getSelectedItem().toString());
                    survey.setPolice_station(police_station.getSelectedItem().toString());
                    survey.setMarket(market.getSelectedItem().toString());
                    survey.setPrimary_health_center(primary_health_center.getSelectedItem().toString());
                    survey.setPrimary_school(primary_school.getSelectedItem().toString());
                    survey.setQuality_of_roads(quality_of_roads.getSelectedItem().toString());
                    survey.setNumber_of_school(number_of_school.getText().toString());*/

                   //progressDialog.show();
                List<Survey> SurveyList =  new ArrayList<>();
                SurveyList.add(survey);
                SubmitSurveyServer(SurveyList);




                }

        });
    }

//    public void insertRowToserver()
//    {
//        int branch_id = Integer.parseInt(getIntent().getStringExtra("branch_id"));
//        String survey_id = getIntent().getStringExtra("survey_id");
//        AsyncTask.execute(() -> {
//            List<Survey> SurveyList =  new ArrayList<>();
//            SurveyList =   AppDatabase.getDatabase(ManageSurveyActivity.this).surveyDao().GetSingleSurveysForVerification(survey_id);
//
//            if(SurveyList.size()>0) {
//                //Log.d("sdssd", SurveyList.get(0).getSurvey_created());
//                SubmitSurveyServer(SurveyList);
//            }
//
//        });
//    }

    public void SubmitSurveyServer(List<Survey> surveyList)
    {
        progressDialog.show();
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder newRequest = request.newBuilder().header("Authorization", prefManager.getString("token"));
                        return chain.proceed(newRequest.build());
                    }
                });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Result> call = service.UpdateSurveyRow(surveyList);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("responseinsert", String.valueOf(response.body().getData()));
                Log.d("responseinsert", String.valueOf(response.body().getMessage()));
                progressDialog.dismiss();
                Intent intent = new Intent(ManageSurveyActivity.this, ViewSurveyActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(ManageSurveyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}