package com.ingenioussys.microfinance.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Survey;

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

public class SyncToServer {

    Activity activity;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    public SyncToServer(Activity activity) {
        this.activity = activity;
        prefManager =  new PrefManager(activity);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Uploading Data To Server ...");
    }

    public void UploadSurvey()
    {
//        AsyncTask.execute(() -> {
//           List<Survey> SurveyList =  new ArrayList<>();
//            SurveyList =   AppDatabase.getDatabase(activity).surveyDao().getAll(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")));
//
//            if(SurveyList.size()>0) {
//                Log.d("sdssd", SurveyList.get(0).getSurvey_created());
//                SubmitSurveyServer(SurveyList);
//            }
//
//        });
    }

    public void UploadSurveyLastRow(int survey_id)
    {
        AsyncTask.execute(() -> {
            List<Survey> SurveyList =  new ArrayList<>();
            //SurveyList =   AppDatabase.getDatabase(activity).surveyDao().get_single_record(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),survey_id);

            if(SurveyList.size()>0) {
                Log.d("sdssd", SurveyList.get(0).getSurvey_created());
                SubmitSurveyServer(SurveyList);
            }

        });
    }

    public void UploadCenter()
    {
//        AsyncTask.execute(() -> {
//            List<Center> CenterList =  new ArrayList<>();
//            CenterList =   AppDatabase.getDatabase(activity).centerDao().getAll();
//
//            if(CenterList.size()>0) {
//                Log.d("center", CenterList.get(0).getCenter_name());
//               // SubmitSurveyServer(SurveyList);
//            }
//
//        });
    }

    public void UploadGroup()
    {
//        AsyncTask.execute(() -> {
//            List<Group> CenterList =  new ArrayList<>();
//            CenterList =   AppDatabase.getDatabase(activity).groupDao().getAll();
//
//            if(CenterList.size()>0) {
//                Log.d("group", CenterList.get(0).getGroup_name());
//                // SubmitSurveyServer(SurveyList);
//            }
//
//        });
    }
    public void SubmitGroupServer(List<Survey> surveyList)
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
        Call<Result> call = service.SubmitSurvey(surveyList);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("response", String.valueOf(response.body().getData()));

                // progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

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
        Call<Result> call = service.SubmitSurvey(surveyList);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("response", String.valueOf(response.body().getData()));

               // progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
}
