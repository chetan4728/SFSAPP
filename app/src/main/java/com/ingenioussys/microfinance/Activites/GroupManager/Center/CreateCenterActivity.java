package com.ingenioussys.microfinance.Activites.GroupManager.Center;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.BranchArea;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

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

public class CreateCenterActivity extends AppCompatActivity {
    HintSpinner area_name;
    PrefManager prefManager;
    EditText created_date,created_by,branch_office,center_name,center_desc;
    Button submit;
    int area_id = 0;
    ProgressDialog progressDialog;
    GPS_Service gps_service;
    List<Center> CenterData;
    List<BranchArea> LoadArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_center);
        getSupportActionBar().setTitle("Create Center");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressDialog =  new ProgressDialog(this);
        prefManager =  new PrefManager(this);

        progressDialog.setMessage("Submitting Data...");
        area_name=  findViewById(R.id.area_name);
        created_date =  findViewById(R.id.created_date);
        branch_office =  findViewById(R.id.branch_office);
        center_name  =  findViewById(R.id.center_name);
        center_desc  =  findViewById(R.id.center_desc);
        gps_service =  new GPS_Service(this);
        new SyncFromServer(this).getBranchArea();
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        branch_office.setText(prefManager.getString("branch_id"));

        created_by =  findViewById(R.id.created_by);
        created_by.setText(prefManager.getString("employee_first_name")+" "+prefManager.getString("employee_last_name"));
        created_date.setText(date);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            LoadArea =   AppDatabase.getDatabase(getApplicationContext()).branchAreaDao().getAll();

            area_name.setAdapter(new HintSpinnerAdapter<BranchArea>(getApplicationContext(), LoadArea, "Select Area") {
                @Override
                public String getLabelFor(BranchArea area) {
                    return area.getArea_name();
                }

            });

            area_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    area_id = LoadArea.get(i).getArea_id();
                   // Toast.makeText(CreateCenterActivity.this, "yrdy"+LoadArea.get(i).getArea_id(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });
      int a_id = Integer.parseInt(prefManager.getString("area_id"));
        area_name.setEnabled(false);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {

                        for (int j = 0; j < LoadArea.size(); j++) {
                            // Toast.makeText(LoanActivityOne.this, ""+ListGroup.get(j).getGroup_id()+""+group_id_get, Toast.LENGTH_SHORT).show();
                            if (LoadArea.get(j).getArea_id() == a_id) {
                                area_name.setSelection(j+1);

                            }
                        }

                    }
                },
                1000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void submit(View view) {

                if(area_id ==0)
                {
                    Toast.makeText(CreateCenterActivity.this, "Please Select Area", Toast.LENGTH_SHORT).show();
                }
                if(center_name.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateCenterActivity.this, "Please Enter Center Name", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    Center center =  new Center();
                    center.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
                    center.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
                    center.setCenter_name(center_name.getText().toString());
                    center.setCenter_desc(center_desc.getText().toString());
                    center.setCreated_date(created_date.getText().toString());
                    center.setLatitude(gps_service.getLatitude());
                    center.setLongitude(gps_service.getLongitude());
                    center.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
                    center.setArea_id(area_id);
                    center.setCenter_status(1);
                    progressDialog.show();
                    CenterData =  new ArrayList<>();
                    CenterData.add(center);
                    SubmitSurveyServer(CenterData);
                   // ExecutorService executor = Executors.newSingleThreadExecutor();
                    //Handler handler = new Handler(Looper.getMainLooper());

//                    executor.execute(() -> {
//                        //Background work here
//                        long last_inserted_id =   AppDatabase.getDatabase(getApplicationContext()).centerDao().insert(center);
//                        progressDialog.setMessage("Saving Data please wait...");
//                        handler.post(() -> {
//                            //UI Thread work here
//                            new android.os.Handler(Looper.getMainLooper()).postDelayed(
//                                    new Runnable() {
//                                        public void run() {
//                                            insertRowToServer(last_inserted_id);
//
//                                        }
//                                    },
//                                    2000);
//
//                        });
//                    });


                }
    }


//    public void insertRowToServer(long last_inserted_id)
//    {
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        executor.execute(() -> {
//            //Background work here
//
//            List<Center> CenterList =    AppDatabase.getDatabase(CreateCenterActivity.this).centerDao().get_single_record(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),last_inserted_id);
//
//            //progressDialog.setMessage("Uploading Data to server wait...");
//            handler.post(() -> {
//                //UI Thread work here
//                if(CenterList.size()>0) {
//
//                    SubmitSurveyServer(CenterList);
//                }
//
//            });
//        });
//
//
//    }

    public void SubmitSurveyServer(List<Center> surveyList)
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
        Call<Result> call = service.SubmitCenterRow(surveyList);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                Intent intent = new Intent(CreateCenterActivity.this, ViewCenterTransactionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(CreateCenterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }

}