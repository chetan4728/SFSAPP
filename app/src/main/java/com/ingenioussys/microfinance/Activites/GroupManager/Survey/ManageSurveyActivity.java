package com.ingenioussys.microfinance.Activites.GroupManager.Survey;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;

import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    HintSpinner bus_service ,auto_service,police_station,market,primary_health_center,primary_school,quality_of_roads,area_name;
    List<String> categories,RoadService;
    ArrayList<Area> areas;
    GPS_Service gps;
    EditText survey_title,created_by,created_date,enter_total_household,enter_minority_household,enter_sc_household,enter_obc_household,enter_st_household;
    EditText enter_area_acres,enter_water_reservolr,enter_rain_fed,enter_forest_land,enter_irrigated_area,enter_crop_name,enter_under_cultivation;
    EditText enter_per_acre_yield,enter_crop_name_second,enter_per_acre_yield_second,enter_crop_name_third,enter_under_cultivation_third,enter_per_acre_yield_third;
    EditText enter_crop_name_fourth,enter_under_cultivation_fourth,enter_per_acre_yield_fourth,enter_number_of_hotels,enter_number_of_utensil_shops,enter_number_of_tea_shops;
    EditText enter_number_of_agri_shops,enter_number_of_kirana_shops,enter_number_of_agree_proce_unit,enter_number_of_tailoring_shops,enter_number_of_pan_shops,enter_number_of_cycle_shops;
    EditText enter_number_of_other_shops,enter_number_of_repair_service_shops,number_of_school,enter_dairy_society_number,enter_dairy_society_client,enter_farmers_club_number;
    EditText enter_farmers_club_client,enter_shgs_number,enter_shgs_client,enter_co_operative_number,enter_co_operative_client,enter_co_operative_branch_number;
    EditText enter_co_operative_branch_client,enter_grameem_branch_number,enter_grameem_branch_client,enter_comercial_branch_number,enter_comercial_branch_client,enter_mfi_number_1;
        EditText enter_mfi_client_1,enter_mfi_name_1,enter_mfi_name_2,enter_mfi_number_2,enter_mfi_client_2,enter_mfi_name_3,enter_mfi_number_3;
    EditText enter_mfi_client_3,enter_mfi_name_4,enter_mfi_number_4,enter_mfi_client_4,mbb_name,enter_under_cultivation_second;
    PrefManager prefManager;
    Button submit;
    int area_id = 0;
    ProgressDialog progressDialog;
    ArrayList<Survey> surveyArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey);
        getSupportActionBar().setTitle("Survey Form");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        new SyncFromServer(this).getArea();
        prefManager =  new PrefManager(this);
        gps =  new GPS_Service(ManageSurveyActivity.this);
        bus_service =  findViewById(R.id.bus_service);
        survey_title =  findViewById(R.id.survey_title);
        auto_service =  findViewById(R.id.auto_service);
        police_station =  findViewById(R.id.police_station);
        police_station =  findViewById(R.id.police_station);
        created_date =  findViewById(R.id.created_date);
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
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        created_date.setText(date);
        created_by =  findViewById(R.id.created_by);
        created_by.setText(prefManager.getString("employee_first_name")+" "+prefManager.getString("employee_last_name"));
        market =  findViewById(R.id.market);
        primary_health_center =  findViewById(R.id.primary_health_center);
        primary_school =  findViewById(R.id.primary_school);
        quality_of_roads =  findViewById(R.id.quality_of_roads);
        area_name=  findViewById(R.id.area_name);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Submitting Data...");

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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
          List<Area>  List =   AppDatabase.getDatabase(getApplicationContext()).areaDao().getAll();


            area_name.setAdapter(new HintSpinnerAdapter<Area>(getApplicationContext(), List, "Select Area") {
                @Override
                public String getLabelFor(Area area) {
                    return area.getArea_name();
                }

            });

            area_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    area_id = List.get(i).getAssign_area_id();
                    //Toast.makeText(TakeSurveyActivity.this, ""+areas.get(i).getAreaId(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(area_id ==0)
                {
                    Toast.makeText(ManageSurveyActivity.this, "Please Select Area", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    get_survey_application_no();


                }
            }
        });
    }
    public String get_survey_application_no()
    {
        surveyArrayList =  new ArrayList<>();
        //Toast.makeText(this, ""+prefManager.getString("area_id"), Toast.LENGTH_SHORT).show();
        String loanApplication_no = "";
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
        Call<Result> call = service.createSurveyUniqeNo(prefManager.getString("bank_id"),area_id,prefManager.getString("branch_id"), prefManager.getString("area_id"),prefManager.getString("bank_prefix"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("testadfasdf", response.body().getData().toString());
                //loan_application_no = response.body().getMessage();
                try {
                    JSONArray array =  new JSONArray(response.body().getData().toString());

                    //Toast.makeText(ManageSurveyActivity.this, ""+  array.getJSONObject(0).getString("survey_no"), Toast.LENGTH_SHORT).show();

                   String Survey_uniqe_id = array.getJSONObject(0).getString("survey_no");

                    Survey survey =  new Survey();
                    survey.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
                    survey.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
                    survey.setSurvey_uniqe_id(Survey_uniqe_id);
                    survey.setArea_id(area_id);
                    survey.setSurvey_title(survey_title.getText().toString());
                    survey.setMbb_name(mbb_name.getText().toString());
                    survey.setSurvey_created(created_date.getText().toString());
                    survey.setSurvey_created_by(Integer.parseInt(prefManager.getString("employee_id")));
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
                    survey.setSurvey_lat(gps.getLatitude());
                    survey.setSurvey_long(gps.getLongitude());
                    survey.setBus_service(""+bus_service.getSelectedItem().toString());
                    survey.setAuto_service(""+auto_service.getSelectedItem().toString());
                    survey.setPolice_station(""+police_station.getSelectedItem().toString());
                    survey.setMarket(""+market.getSelectedItem().toString());
                    survey.setPrimary_health_center(""+primary_health_center.getSelectedItem().toString());
                    survey.setPrimary_school(""+primary_school.getSelectedItem().toString());
                    survey.setQuality_of_roads(""+quality_of_roads.getSelectedItem().toString());
                    survey.setNumber_of_school(number_of_school.getText().toString());
                    survey.setSurver_verfication_status(1);
                    surveyArrayList.add(survey);


                    progressDialog.show();
                    SubmitSurveyServer(surveyArrayList);

//                    ExecutorService executor = Executors.newSingleThreadExecutor();
//                    Handler handler = new Handler(Looper.getMainLooper());
//
//                    executor.execute(() -> {
//                        //Background work here
////                        AppDatabase.getDatabase(getApplicationContext()).surveyDao().insert(survey);
//                        progressDialog.setMessage("Saving Data please wait...");
//                        handler.post(() -> {
//                            //UI Thread work here
//                            new android.os.Handler(Looper.getMainLooper()).postDelayed(
//                                    new Runnable() {
//                                        public void run() {
//                                            insertRowToserver(Survey_uniqe_id);
//
//                                        }
//                                    },
//                                    2000);
//
//                        });
//                    });






                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //  ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(ManageSurveyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return loanApplication_no;
    }
    public void insertRowToserver(String last_inserted_id)
    {


        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here

//            List<Survey> SurveyList =   AppDatabase.getDatabase(ManageSurveyActivity.this).surveyDao().get_single_record(last_inserted_id);
            //Log.d("sdssd"+last_inserted_id, SurveyList.get(0).getSurvey_created());
            //progressDialog.setMessage("Uploading Data to server wait...");
//            handler.post(() -> {
//                //UI Thread work here
//                if(SurveyList.size()>0) {
//
//                    SubmitSurveyServer(SurveyList);
//                }
//
//            });
        });



       /* AsyncTask.execute(() -> {
            List<Survey> SurveyList =  new ArrayList<>();
            SurveyList =   AppDatabase.getDatabase(ManageSurveyActivity.this).surveyDao().get_single_record(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),last_inserted_id);

            if(SurveyList.size()>0) {
                //Log.d("sdssd", SurveyList.get(0).getSurvey_created());
                SubmitSurveyServer(SurveyList);
            }

        });*/
    }

    public void SubmitSurveyServer(List<Survey> surveyList)
    {
        //progressDialog.show();
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
        Call<Result> call = service.SubmitSurveyRow(surveyList);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("responseinsert", String.valueOf(response.body().getData()));

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