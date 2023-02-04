package com.ingenioussys.microfinance.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.Dao.CGTDao;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.BranchArea;
import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.CGTProcess;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Day;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class SyncFromServer {
    Activity activity;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    ArrayList<Center> CenterList;
    public SyncFromServer(Activity activity) {
        this.activity = activity;
        prefManager =  new PrefManager(activity);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Fetching Data From Server ...");
       // getBranchArea();
        //getArea();
      //  get_process();
       // get_day();
       // get_centers();
    }




    public void getBranchArea()
    {

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
        Call<Result> call = service.GetBranchArea(prefManager.getString("bank_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                        Log.d("araaaa",jsonarray.toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            BranchArea aread =  new BranchArea();


                            aread.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            aread.setArea_id(jsonarray.getJSONObject(i).getInt("area_id"));
                            aread.setLatitute(jsonarray.getJSONObject(i).getDouble("latitude"));
                            aread.setLongitude(jsonarray.getJSONObject(i).getDouble("longitude"));
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(activity).branchAreaDao().delete(aread);
                                AppDatabase.getDatabase(activity).branchAreaDao().insert(aread);
                            });

                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();



                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void get_process()
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
        Call<Result> call = service.manage_process();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            CGTProcess cgtProcess =  new CGTProcess();
                            cgtProcess.setProcess_id(jsonarray.getJSONObject(i).getInt("process_id"));
                            cgtProcess.setProcess_name(jsonarray.getJSONObject(i).getString("process_name"));
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(activity).cgtProcessDao().delete(cgtProcess);
                                AppDatabase.getDatabase(activity).cgtProcessDao().insert(cgtProcess);
                            });

                        }


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
    public void get_Center_Groups(String center_id)
    {
//        progressDialog.show();
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
//        okHttpClientBuilder
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder newRequest = request.newBuilder().header("Authorization", prefManager.getString("token"));
//                        return chain.proceed(newRequest.build());
//                    }
//                });
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(SERVER_URL)
//                .client(okHttpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        APIService service = retrofit.create(APIService.class);
//        Call<Result> call = service.LoadCenterGroupsTable(prefManager.getString("bank_id"),prefManager.getString("branch_id"),center_id,prefManager.getString("employee_id"));
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Log.d("groups", String.valueOf(response.body().getData()));
//                if (response.body().getError()) {
//                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//
//                    try {
//                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
//
//                        AsyncTask.execute(() -> {
//                            AppDatabase.getDatabase(activity).groupDao().deleteAllRecord();
//
//                        });
//                        for(int i=0; i < jsonarray.length() ; i++)
//                        {
//                            Group group =  new Group();
//                            group.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
//                            group.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
//                            group.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
//                            group.setGroup_id(jsonarray.getJSONObject(i).getInt("group_id"));
//                            group.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
//                            group.setMember_limit(jsonarray.getJSONObject(i).getInt("member_limit"));
//                            group.setContact_number(jsonarray.getJSONObject(i).getString("contact_number"));
//                            group.setGroup_name(jsonarray.getJSONObject(i).getString("group_name"));
//                            group.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
//                            group.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));
//                            group.setLatitude(jsonarray.getJSONObject(i).getDouble("latitude"));
//                            group.setLongitude(jsonarray.getJSONObject(i).getDouble("longitude"));
//                            group.setGroupStatus(jsonarray.getJSONObject(i).getInt("GroupStatus"));
//                            group.setVerified_by(jsonarray.getJSONObject(i).getInt("verified_by"));
//                            group.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));
//                            AsyncTask.execute(() -> {
//                                AppDatabase.getDatabase(activity).groupDao().delete(group);
//                                AppDatabase.getDatabase(activity).groupDao().insert(group);
//                            });
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
    public void get_Groups(String center_id)
    {
//        progressDialog.show();
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
//        okHttpClientBuilder
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder newRequest = request.newBuilder().header("Authorization", prefManager.getString("token"));
//                        return chain.proceed(newRequest.build());
//                    }
//                });
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(SERVER_URL)
//                .client(okHttpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        APIService service = retrofit.create(APIService.class);
//        Call<Result> call = service.LoadGroupsTable(prefManager.getString("bank_id"),prefManager.getString("branch_id"),prefManager.getString("employee_id"));
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Log.d("groups", String.valueOf(response.body().getData()));
//                if (response.body().getError()) {
//                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//
//                    try {
//                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
//
//                        AsyncTask.execute(() -> {
//                            AppDatabase.getDatabase(activity).groupDao().deleteAllRecord();
//
//                        });
//                        for(int i=0; i < jsonarray.length() ; i++)
//                        {
//                            Group group =  new Group();
//                            group.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
//                            group.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
//                            group.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
//                            group.setGroup_id(jsonarray.getJSONObject(i).getInt("group_id"));
//                            group.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
//                            group.setMember_limit(jsonarray.getJSONObject(i).getInt("member_limit"));
//                            group.setContact_number(jsonarray.getJSONObject(i).getString("contact_number"));
//                            group.setGroup_name(jsonarray.getJSONObject(i).getString("group_name"));
//                            group.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
//                            group.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));
//                            group.setLatitude(jsonarray.getJSONObject(i).getDouble("latitude"));
//                            group.setLongitude(jsonarray.getJSONObject(i).getDouble("longitude"));
//                            group.setGroupStatus(jsonarray.getJSONObject(i).getInt("GroupStatus"));
//                            group.setVerified_by(jsonarray.getJSONObject(i).getInt("verified_by"));
//                            group.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));
//                            AsyncTask.execute(() -> {
//                                AppDatabase.getDatabase(activity).groupDao().delete(group);
//                                AppDatabase.getDatabase(activity).groupDao().insert(group);
//                            });
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }


    public void get_centers()
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
        Call<Result> call = service.LoadCenterTable(prefManager.getString("bank_id"),prefManager.getString("branch_id"),prefManager.getString("employee_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Center center =  new Center();


                            center.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
                            center.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            center.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
                            center.setCenter_desc(jsonarray.getJSONObject(i).getString("center_desc"));
                            center.setArea_id(jsonarray.getJSONObject(i).getInt("area_id"));
                            center.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            center.setCenter_status(jsonarray.getJSONObject(i).getInt("center_status"));
                            center.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
                            center.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));
                            center.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));

                        }

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

    public void get_cgt()
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
        Call<Result> call = service.LoadCGTData(prefManager.getString("bank_id"),prefManager.getString("employee_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {

                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {

                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                        Log.d("cgt_data",jsonarray.toString());

                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            CGT cgt =  new CGT();
                            cgt.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
                            cgt.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            cgt.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
                            cgt.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            cgt.setArea_id(jsonarray.getJSONObject(i).getInt("area_id"));
                            cgt.setProcess_id(jsonarray.getJSONObject(i).getInt("process_id"));
                            cgt.setCgt_id(jsonarray.getJSONObject(i).getInt("cgt_id"));
                            cgt.setPicture_path(jsonarray.getJSONObject(i).getString("picture_path"));
                            cgt.setNumber_of_customers(jsonarray.getJSONObject(i).getInt("number_of_customers"));
                            cgt.setCgt_added_by(jsonarray.getJSONObject(i).getInt("cgt_added_by"));
                            cgt.setCgt_added_at(jsonarray.getJSONObject(i).getString("cgt_added_at"));


                        }

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

    public void get_day()
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
        Call<Result> call = service.manage_day();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError()) {
                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Day day =  new Day();


                            day.setDay_id(jsonarray.getJSONObject(i).getInt("day_id"));
                            day.setDay_name(jsonarray.getJSONObject(i).getString("day_name"));
                            day.setDay_activity(jsonarray.getJSONObject(i).getString("day_activity"));
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(activity).dayDao().delete(day);
                                AppDatabase.getDatabase(activity).dayDao().insert(day);
                            });

                        }





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
    public void getArea()
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
        Call<Result> call = service.GetArea(prefManager.getString("bank_id"),prefManager.getString("employee_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("response"+prefManager.getString("branch_id") +"---" +prefManager.getString("employee_id"), String.valueOf(response.body().getData()));
                if (response.body().getError()) {
                    Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Area aread =  new Area();


                            aread.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            aread.setAssign_area_id(jsonarray.getJSONObject(i).getInt("assign_area_id"));
                            aread.setLongitude(jsonarray.getJSONObject(i).getInt("latitude"));
                            aread.setLongitude(jsonarray.getJSONObject(i).getInt("longitude"));
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(activity).areaDao().delete(aread);
                                AppDatabase.getDatabase(activity).areaDao().insert(aread);
                            });

                        }





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


    public void getSurveys()
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
        Call<Result> call = service.getSurveys(prefManager.getString("bank_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //Log.d("response"+prefManager.getString("branch_id") +"---" +prefManager.getString("employee_id"), String.valueOf(response.body().getData()));
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
                            //survey.setSurvey_area_name(jsonarray.getJSONObject(i).getString("area_name"));
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

                        }

                      /*  for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Area aread =  new Area();
                            aread.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            aread.setAssign_area_id(jsonarray.getJSONObject(i).getInt("assign_area_id"));
                            aread.setLongitude(jsonarray.getJSONObject(i).getInt("latitude"));
                            aread.setLongitude(jsonarray.getJSONObject(i).getInt("longitude"));
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(activity).areaDao().delete(aread);
                                AppDatabase.getDatabase(activity).areaDao().insert(aread);
                            });

                        }*/


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
}
