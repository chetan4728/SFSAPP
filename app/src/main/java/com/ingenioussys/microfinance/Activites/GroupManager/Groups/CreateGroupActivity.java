package com.ingenioussys.microfinance.Activites.GroupManager.Groups;

import androidx.appcompat.app.AppCompatActivity;

import com.ingenioussys.microfinance.Activites.GroupManager.Center.ViewCenterTransactionActivity;
import com.ingenioussys.microfinance.Adapter.CenterTransactionAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.Service.GPS_Service;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class CreateGroupActivity extends AppCompatActivity {
    HintSpinner center_name,member_limit;
    ProgressDialog progressDialog;
    PrefManager prefManager;
    EditText created_date,created_by,branch_office,group_name,contact_person,contact_number;
    int center_id = 0;
    List<Center> centerArrayList;
   // int area_id = 0;
    Button submit;
    List<Group> groupArrayList;
    GPS_Service gps_service;
    int member_limit_number = 0;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        getSupportActionBar().setTitle("Create Group");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        new SyncFromServer(this).get_centers();
        progressDialog =  new ProgressDialog(this);
        prefManager =  new PrefManager(this);
        progressDialog.setMessage("Submitting Data...");
        center_name=  findViewById(R.id.center_name);
        submit =  findViewById(R.id.submit);
        created_date =  findViewById(R.id.created_date);
        created_by =  findViewById(R.id.created_by);
        branch_office =  findViewById(R.id.branch_office);
        contact_person =  findViewById(R.id.contact_person);
        contact_number =  findViewById(R.id.contact_number);
        group_name  =  findViewById(R.id.group_name);
       // gps_service = new GPS_Service(this);
        member_limit =  findViewById(R.id.member_limit);
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        created_by.setText(prefManager.getString("employee_first_name")+" "+prefManager.getString("employee_last_name"));
        created_date.setText(date);
        branch_office.setText(prefManager.getString("branch_id"));
        LoadCenterData();
        List<String> limit =  new ArrayList<>();
        limit.add("4");
        limit.add("5");
        limit.add("6");
        limit.add("7");
        limit.add("8");

        member_limit.setAdapter(new HintSpinnerAdapter<String>(this, limit, "Member Limit"));
        member_limit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member_limit_number = Integer.parseInt(limit.get(position));
               // Toast.makeText(CreateGroupActivity.this, ""+limit.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(center_id ==0)
                {
                    Toast.makeText(CreateGroupActivity.this, "Please Select Center", Toast.LENGTH_SHORT).show();
                }
                else if(group_name.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateGroupActivity.this, "Please Enter Group Name", Toast.LENGTH_SHORT).show();
                }
                else if(member_limit_number ==0)
                {
                    Toast.makeText(CreateGroupActivity.this, "Please Select Group Members", Toast.LENGTH_SHORT).show();
                }

                else
                {

                    //Toast.makeText(CreateGroupActivity.this, ""+prefManager.getString("branch_id"), Toast.LENGTH_SHORT).show();

                    Group group =  new Group();
                    group.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
                    group.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
                    group.setGroup_name(group_name.getText().toString());
                    group.setCenter_id(center_id);
                    group.setCreated_date(created_date.getText().toString());
                    group.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
                    group.setContact_person(contact_person.getText().toString());
                    group.setContact_number(contact_number.getText().toString());
                    //group.setLatitude(gps_service.getLatitude());
                   // group.setLongitude(gps_service.getLongitude());
                    group.setGroupStatus(1);
                    group.setMember_limit(member_limit_number);
                    groupArrayList =  new ArrayList<>();
                    groupArrayList.add(group);
                    SubmitSurveyServer(groupArrayList);






                }
            }
        });
    }

    public void LoadCenterData()
    {
        centerArrayList =  new ArrayList<>();
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

                } else {



                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                            Center center =  new Center();


                            center.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
                            Toast.makeText(CreateGroupActivity.this, ""+jsonarray.getJSONObject(i).getInt("center_id"), Toast.LENGTH_SHORT).show();
                            center.setCenter_no(jsonarray.getJSONObject(i).getInt("center_no"));
                            center.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            center.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
                            center.setCenter_desc(jsonarray.getJSONObject(i).getString("center_desc"));
                            center.setArea_id(jsonarray.getJSONObject(i).getInt("area_id"));
                            center.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            center.setCenter_status(jsonarray.getJSONObject(i).getInt("center_status"));
                            center.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
                            center.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));
                            center.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));
                            centerArrayList.add(center);

                        }
                        center_name.setAdapter(new HintSpinnerAdapter<Center>(getApplicationContext(), centerArrayList, "Select Center") {
                            @Override
                            public String getLabelFor(Center center) {
                                return center.getCenter_name();
                            }

                        });

                        center_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                center_id = centerArrayList.get(i).getCenter_id();
                                Toast.makeText(CreateGroupActivity.this, ""+centerArrayList.get(i).getCenter_id(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

//                                        no_data.setVisibility(View.GONE);

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
                //Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



//        centerArrayList =  new ArrayList<>();
//        progressDialog.show();
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        executor.execute(() -> {
//
//                            centerArrayList =   AppDatabase.getDatabase(getApplicationContext()).centerDao().getAllByUser(Integer.parseInt(prefManager.getString("employee_id")));
//                            if(centerArrayList.size()>0) {
//
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        CenterTransactionAdapter surveyAdapter =  new CenterTransactionAdapter(ViewCenterTransactionActivity.this,centerArrayList);
//                                        centerList.setAdapter(surveyAdapter);
//                                        centerList.setVisibility(View.VISIBLE);
//                                        no_data.setVisibility(View.GONE);
//                                    }
//                                });
//                            }
//                            else
//                            {
//                                centerList.setVisibility(View.GONE);
//                                no_data.setVisibility(View.VISIBLE);
//                            }
//                            progressDialog.dismiss();
//
//                        });
//                    }
//                },
//                2000);



    }



    public void SubmitSurveyServer(List<Group> list)
    {
       // Toast.makeText(CreateGroupActivity.this, ""+list.getGroup_name(), Toast.LENGTH_SHORT).show();
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
        Call<Result> call = service.SubmitGroupRow(list);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
             // Log.d("responseinsert", String.valueOf(response.body().getMessage()));
                Intent intent = new Intent(CreateGroupActivity.this, ViewGroupTransactionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(CreateGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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