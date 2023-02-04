package com.ingenioussys.microfinance.Activites.GroupManager.Survey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Adapter.SurveyAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;

import org.json.JSONArray;
import org.json.JSONException;

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

public class ViewSurveyActivity extends AppCompatActivity {
    Activity activity;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    ListView list;
    ArrayList<Survey> surveyArrayList;
    TextView no_data;
    List<Survey> SurveyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_survey);
        getSupportActionBar().setTitle("My Surveys");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list =  findViewById(R.id.list);
        no_data =  findViewById(R.id.no_data);
        activity =  this;
        prefManager =  new PrefManager(this);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Loading Data ...");
       // new SyncFromServer(this).getSurveys();
        LoadSurveys();
    }



    public void LoadSurveys()
    {

        surveyArrayList =  new ArrayList<>();
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
        Call<Result> call = service.getSurveys(prefManager.getString("bank_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("response"+prefManager.getString("branch_id") +"---" +prefManager.getString("employee_id"), String.valueOf(response.body().getData()));
                if (response.body().getError()) {
                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                        //Log.d("responseGetSurvey",jsonarray.toString());

                        for(int i=0;i<jsonarray.length();i++)
                        {
                            Survey survey =  new Survey();
                            survey.setId(jsonarray.getJSONObject(i).getInt("id"));
                            survey.setSurvey_title(jsonarray.getJSONObject(i).getString("survey_title"));
                            survey.setSurvey_uniqe_id(jsonarray.getJSONObject(i).getString("survey_uniqe_id"));
                            survey.setSurvey_id(jsonarray.getJSONObject(i).getInt("survey_id"));
                            survey.setArea_id(jsonarray.getJSONObject(i).getInt("area_id"));
                           // survey.setSurvey_area_name(jsonarray.getJSONObject(i).getString("area_name"));
                            survey.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            survey.setSurvey_created_by(jsonarray.getJSONObject(i).getInt("survey_created_by"));
                            survey.setMbb_name(jsonarray.getJSONObject(i).getString("mbb_name"));
                            survey.setSurvey_created(jsonarray.getJSONObject(i).getString("survey_created"));
                            survey.setTotal_household(jsonarray.getJSONObject(i).getString("total_household"));
                            survey.setMinority_household(jsonarray.getJSONObject(i).getString("minority_household"));
                            survey.setSc_household(jsonarray.getJSONObject(i).getString("sc_household"));
                            survey.setObc_household(jsonarray.getJSONObject(i).getString("obc_household"));
                            survey.setSt_household(jsonarray.getJSONObject(i).getString("st_household"));
                            survey.setArea_acres(jsonarray.getJSONObject(i).getString("area_acres"));
                            survey.setWater_reservolr(jsonarray.getJSONObject(i).getString("water_reservolr"));
                            survey.setRain_fed(jsonarray.getJSONObject(i).getString("rain_fed"));
                            survey.setForest_land(jsonarray.getJSONObject(i).getString("forest_land"));
                            survey.setIrrigated_area(jsonarray.getJSONObject(i).getString("irrigated_area"));
                            survey.setCrop_name(jsonarray.getJSONObject(i).getString("crop_name"));
                            survey.setUnder_cultivation(jsonarray.getJSONObject(i).getString("under_cultivation"));
                            survey.setPer_acre_yield(jsonarray.getJSONObject(i).getString("per_acre_yield"));
                            survey.setCrop_name_second(jsonarray.getJSONObject(i).getString("crop_name_second"));
                            survey.setUnder_cultivation_second(jsonarray.getJSONObject(i).getString("per_acre_yield_second"));
                            survey.setPer_acre_yield_second(jsonarray.getJSONObject(i).getString("under_cultivation_second"));
                            survey.setCrop_name_third(jsonarray.getJSONObject(i).getString("crop_name_third"));
                            survey.setUnder_cultivation_third(jsonarray.getJSONObject(i).getString("under_cultivation_third"));
                            survey.setPer_acre_yield_third(jsonarray.getJSONObject(i).getString("per_acre_yield_third"));
                            survey.setCrop_name_fourth(jsonarray.getJSONObject(i).getString("crop_name_fourth"));
                            survey.setUnder_cultivation_fourth(jsonarray.getJSONObject(i).getString("under_cultivation_fourth"));
                            survey.setPer_acre_yield_fourth(jsonarray.getJSONObject(i).getString("per_acre_yield_fourth"));
                            survey.setNumber_of_hotels(jsonarray.getJSONObject(i).getString("number_of_hotels"));
                            survey.setNumber_of_utensil_shops(jsonarray.getJSONObject(i).getString("number_of_utensil_shops"));
                            survey.setNumber_of_tea_shops(jsonarray.getJSONObject(i).getString("number_of_tea_shops"));
                            survey.setNumber_of_agri_shops(jsonarray.getJSONObject(i).getString("number_of_agri_shops"));
                            survey.setNumber_of_kirana_shops(jsonarray.getJSONObject(i).getString("number_of_kirana_shops"));
                            survey.setNumber_of_tailoring_shops(jsonarray.getJSONObject(i).getString("number_of_tailoring_shops"));
                            survey.setNumber_of_agree_proce_unit(jsonarray.getJSONObject(i).getString("number_of_agree_proce_unit"));
                            survey.setNumber_of_pan_shops(jsonarray.getJSONObject(i).getString("number_of_pan_shops"));
                            survey.setNumber_of_cycle_shops(jsonarray.getJSONObject(i).getString("number_of_cycle_shops"));
                            survey.setNumber_of_other_shops(jsonarray.getJSONObject(i).getString("number_of_other_shops"));
                            survey.setNumber_of_repair_service_shops(jsonarray.getJSONObject(i).getString("number_of_repair_service_shops"));
                            survey.setDairy_society_number(jsonarray.getJSONObject(i).getString("dairy_society_number"));
                            survey.setDairy_society_client(jsonarray.getJSONObject(i).getString("dairy_society_client"));
                            survey.setFarmers_club_number(jsonarray.getJSONObject(i).getString("farmers_club_number"));
                            survey.setFarmers_club_client(jsonarray.getJSONObject(i).getString("farmers_club_client"));
                            survey.setShgs_client(jsonarray.getJSONObject(i).getString("shgs_number"));
                            survey.setShgs_number(jsonarray.getJSONObject(i).getString("shgs_client"));
                            survey.setCo_operative_number(jsonarray.getJSONObject(i).getString("co_operative_number"));
                            survey.setCo_operative_client(jsonarray.getJSONObject(i).getString("co_operative_client"));
                            survey.setCo_operative_branch_number(jsonarray.getJSONObject(i).getString("co_operative_branch_number"));
                            survey.setCo_operative_branch_client(jsonarray.getJSONObject(i).getString("co_operative_branch_client"));
                            survey.setGrameem_branch_client(jsonarray.getJSONObject(i).getString("grameem_branch_number"));
                            survey.setGrameem_branch_number(jsonarray.getJSONObject(i).getString("grameem_branch_number"));
                            survey.setComercial_branch_client(jsonarray.getJSONObject(i).getString("comercial_branch_client"));
                            survey.setComercial_branch_number(jsonarray.getJSONObject(i).getString("comercial_branch_number"));
                            survey.setMfi_client_1(jsonarray.getJSONObject(i).getString("mfi_client_1"));
                            survey.setMfi_client_2(jsonarray.getJSONObject(i).getString("mfi_client_2"));
                            survey.setMfi_client_3(jsonarray.getJSONObject(i).getString("mfi_client_3"));
                            survey.setMfi_client_4(jsonarray.getJSONObject(i).getString("mfi_client_4"));
                            survey.setMfi_name_1(jsonarray.getJSONObject(i).getString("mfi_name_1"));
                            survey.setMfi_name_2(jsonarray.getJSONObject(i).getString("mfi_name_2"));
                            survey.setMfi_name_3(jsonarray.getJSONObject(i).getString("mfi_name_3"));
                            survey.setMfi_name_4(jsonarray.getJSONObject(i).getString("mfi_name_4"));
                            survey.setSurvey_lat(jsonarray.getJSONObject(i).getDouble("survey_lat"));
                            survey.setSurvey_long(jsonarray.getJSONObject(i).getDouble("survey_long"));
                            survey.setBus_service(jsonarray.getJSONObject(i).getString("bus_service"));
                            survey.setAuto_service(jsonarray.getJSONObject(i).getString("auto_service"));
                            survey.setPolice_station(jsonarray.getJSONObject(i).getString("police_station"));
                            survey.setMarket(jsonarray.getJSONObject(i).getString("market"));
                            survey.setPrimary_health_center(jsonarray.getJSONObject(i).getString("primary_health_center"));
                            survey.setPrimary_school(jsonarray.getJSONObject(i).getString("primary_school"));
                            survey.setQuality_of_roads(jsonarray.getJSONObject(i).getString("quality_of_roads"));
                            survey.setNumber_of_school(jsonarray.getJSONObject(i).getString("number_of_school"));
                            survey.setSurver_verfication_status(jsonarray.getJSONObject(i).getInt("surver_verfication_status"));
                            survey.setSuvery_verfied_by(jsonarray.getJSONObject(i).getInt("suvery_verfied_by"));
                            surveyArrayList.add(survey);
                        }

                        SurveyAdapter surveyAdapter =  new SurveyAdapter(ViewSurveyActivity.this,surveyArrayList);
                                        list.setAdapter(surveyAdapter);
                                        list.setVisibility(View.VISIBLE);
                                        no_data.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        AsyncTask.execute(() -> {
//
//
//                            Log.d("details",prefManager.getString("employee_id")+"--"+prefManager.getString("branch_id"));
//
//                            SurveyList =  new ArrayList<>();
////                            SurveyList =   AppDatabase.getDatabase(getApplicationContext()).surveyDao().getAll(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")));
//
//                            if(SurveyList.size()>0) {
//
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        SurveyAdapter surveyAdapter =  new SurveyAdapter(ViewSurveyActivity.this,SurveyList);
//                                        list.setAdapter(surveyAdapter);
//                                        list.setVisibility(View.VISIBLE);
//                                        no_data.setVisibility(View.GONE);
//                                    }
//                                });
//                            }
//                            else
//                            {
//                                list.setVisibility(View.GONE);
//                                no_data.setVisibility(View.VISIBLE);
//                            }
//                            progressDialog.dismiss();
//
//                        });
//                    }
//                },
//                2000);



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.file_menu, menu);

        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.add_survey) {
                Intent intent =  new Intent(ViewSurveyActivity.this, ManageSurveyActivity.class);
                startActivity(intent);
                finish();
               return true;
            }

        return super.onOptionsItemSelected(item);





    }
}