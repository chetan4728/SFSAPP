package com.ingenioussys.microfinance.Activites.GroupManager.CGT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.JsonArray;
import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment.LoanActivityOne;
import com.ingenioussys.microfinance.Adapter.LoanApplicationAdapter;
import com.ingenioussys.microfinance.Adapter.LoanApplicationMemberAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.BranchArea;
import com.ingenioussys.microfinance.model.CGT;
import com.ingenioussys.microfinance.model.CGTProcess;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Day;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class CGTProcessActivity extends AppCompatActivity {

    HintSpinner area_name,center_name,process_name,process_day,group_name;
    EditText process_activity;
    Button submit,reset;
    int center_id = 0;
    int group_id = 0;
    int area_id = 0;
    int process_id = 0;
    int day_id = 0;
    int number_of_customers = 0;
    String image_string;
    boolean upload_img = false;
    ImageView pr;
    List<Center> centerArrayList;
    List<Group> groupArrayList;
    List<CGT> CGTADD;
    Button upload_picture;
    ProgressDialog progressDialog;
    PrefManager prefManager;
    String Image_name ="";
    Uri imageUri;
    List<BranchArea> AreaList;
    List<Center> centerList;
    List<LoanApplication> loanApplications;


    List<Group> ListGroup;
    ListView member_list;
    List<String> application_numbers  =  new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_g_t_process);
        getSupportActionBar().setTitle("CGT");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        area_name = findViewById(R.id.area_name);
        center_name = findViewById(R.id.center_name);


        group_name = findViewById(R.id.group_name);
        process_activity = findViewById(R.id.process_activity);
        submit  = findViewById(R.id.submit);
        pr = findViewById(R.id.pr);
        member_list = findViewById(R.id.member_list);
        member_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        prefManager =  new PrefManager(this);
        //new SyncFromServer(this).get_centers();
       // new SyncFromServer(this).getBranchArea();
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Submitting Data...");
        upload_picture = findViewById(R.id.upload_picture);
        upload_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(CGTProcessActivity.this)
                        .crop()
                        .cameraOnly()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        reset  = findViewById(R.id.reset);
        load_centers();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AreaList =   AppDatabase.getDatabase(getApplicationContext()).branchAreaDao().getAll();

            area_name.setAdapter(new HintSpinnerAdapter<BranchArea>(getApplicationContext(), AreaList, "Select Area") {
                @Override
                public String getLabelFor(BranchArea area) {
                    return area.getArea_name();
                }

            });

            area_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    area_id = AreaList.get(i).getArea_id();


                   // Toast.makeText(CreateCenterActivity.this, ""+List.get(i).getAreaId(), Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });











        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 if(center_id==0)
                {
                    Toast.makeText(CGTProcessActivity.this, "Please Select Center", Toast.LENGTH_SHORT).show();
                }



                else  if(upload_img==false)
                {
                    Toast.makeText(CGTProcessActivity.this, "Please Upload Photo", Toast.LENGTH_SHORT).show();
                }
                else
                {



                    JsonArray idArray =  new JsonArray();

                    for(int j=0;j<application_numbers.size();j++)
                    {
                        idArray.add(application_numbers.get(j));
                    }
                    JSONObject object =  new JSONObject();
                    try {
                        object.put("member_ids",idArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d("member_ids",object.toString());

                    CGT cgt =  new CGT();
                    cgt.setArea_id(Integer.parseInt(prefManager.getString("area_id")));
                    cgt.setCenter_id(center_id);
                    cgt.setGroup_id(group_id);
                    cgt.setProcess_id(process_id);
                    cgt.setPicture_path(Image_name);
                    cgt.setCGT_ids(object.toString());
                    cgt.setNumber_of_customers(application_numbers.size());
                    cgt.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
                    cgt.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
                    cgt.setCgt_added_by(Integer.parseInt(prefManager.getString("employee_id")));
                    String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    cgt.setCgt_added_at(date);
                    CGTADD =  new ArrayList<>();
                    CGTADD.add(cgt);
                    SubmitServer(CGTADD);
//                    progressDialog.show();
//                    new android.os.Handler().postDelayed(
//                            new Runnable() {
//                                public void run() {
//                                    executor.execute(() -> {
//                                        long last_inserted_id =   AppDatabase.getDatabase(getApplicationContext()).cgtDao().insert(cgt);
//                                        // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
//
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                new android.os.Handler().postDelayed(
//                                                        new Runnable() {
//                                                            public void run() {
//                                                                insertRowToserver(last_inserted_id);
//                                                                progressDialog.dismiss();
//                                                            }
//                                                        },
//                                                        1000);
//                                            }
//                                        });
//
//
//
//                                    });
//                                }
//                            },
//                            2000);
                }
            }
        });

        if(getIntent().getIntExtra("cgt_id",0)!=0)
        {
            load_details();
        }



    }

    public  void load_centers()
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
                            //Toast.makeText(CreateGroupActivity.this, ""+jsonarray.getJSONObject(i).getInt("center_id"), Toast.LENGTH_SHORT).show();
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
                                area_id = centerArrayList.get(i).getArea_id();
                                load_groups(center_id);
                                //Toast.makeText(LoanActivityOne.this, ""+centerArrayList.get(i).getCenter_id(), Toast.LENGTH_SHORT).show();

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



    }

    public  void load_groups(int center_id)
    {
        groupArrayList =  new ArrayList<>();
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
        Call<Result> call = service.LoadCenterGroupsTable(prefManager.getString("bank_id"),prefManager.getString("branch_id"), String.valueOf(center_id),prefManager.getString("employee_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("groups", String.valueOf(response.body().getData()));
                if (response.body().getError()) {
                    // Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONArray jsonarray = new JSONArray(response.body().getData().toString());


                        for(int i=0; i < jsonarray.length() ; i++)
                        {
                          //  Toast.makeText(LoanActivityOne.this, ""+jsonarray.getJSONObject(i).getString("group_name"), Toast.LENGTH_SHORT).show();
                            Group group =  new Group();
                            group.setCenter_id(jsonarray.getJSONObject(i).getInt("center_id"));
                            group.setArea_name(jsonarray.getJSONObject(i).getString("area_name"));
                            group.setCenter_name(jsonarray.getJSONObject(i).getString("center_name"));
                            group.setGroup_id(jsonarray.getJSONObject(i).getInt("group_id"));
                            group.setBranch_id(jsonarray.getJSONObject(i).getInt("branch_id"));
                            group.setMember_limit(jsonarray.getJSONObject(i).getInt("member_limit"));
                            group.setContact_number(jsonarray.getJSONObject(i).getString("contact_number"));
                            group.setGroup_name(jsonarray.getJSONObject(i).getString("group_name"));
                            group.setCreated_date(jsonarray.getJSONObject(i).getString("created_date"));
                            group.setCreated_by(jsonarray.getJSONObject(i).getInt("created_by"));
                            group.setLatitude(jsonarray.getJSONObject(i).getDouble("latitude"));
                            group.setLongitude(jsonarray.getJSONObject(i).getDouble("longitude"));
                            group.setGroupStatus(jsonarray.getJSONObject(i).getInt("GroupStatus"));
                            group.setVerified_by(jsonarray.getJSONObject(i).getInt("verified_by"));
                            group.setFs_name(jsonarray.getJSONObject(i).getString("fs_name"));

                            groupArrayList.add(group);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    group_name.setAdapter(new HintSpinnerAdapter<Group>(getApplicationContext(), groupArrayList, "Select Group") {
                        @Override
                        public String getLabelFor(Group group) {
                            return group.getGroup_name();
                        }

                    });
                    group_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            group_id = groupArrayList.get(i).getGroup_id();
                            get_group_members(center_id,groupArrayList.get(i).getGroup_id());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    public void get_group_members(int center_id,int group_id)
    {
        loanApplications = new ArrayList<>();
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
        Call<Result> call = service.get_group_members(String.valueOf(center_id), String.valueOf(group_id),prefManager.getString("bank_id"),prefManager.getString("branch_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Log.d("member_repsonse",response.body().getData().toString());

                try {
                    JSONArray jsonArray = new JSONArray(response.body().getData().toString());
                     for(int i=0;i<jsonArray.length();i++) {
                        // Toast.makeText(CGTProcessActivity.this, ""+jsonArray.getJSONObject(i).getString("applicant_name"), Toast.LENGTH_SHORT).show();
                         LoanApplication loanApplication = new LoanApplication();
                         loanApplication.setApplicant_name(jsonArray.getJSONObject(i).getString("applicant_name"));
                         loanApplication.setLoan_application_number(jsonArray.getJSONObject(i).getString("loan_application_number"));
                         member_list.setItemChecked(i,loanApplication.isActive());
                         loanApplications.add(loanApplication);


                     }

                    //ArrayAdapter arrayAdapter = new ArrayAdapter(CGTProcessActivity.this, android.R.layout.simple_list_item_checked ,  loanApplications);

                    LoanApplicationMemberAdapter loanApplicationAdapter =  new LoanApplicationMemberAdapter(CGTProcessActivity.this,loanApplications);
                    member_list.setAdapter(loanApplicationAdapter);
                    setListViewHeightBasedOnChildren(member_list);

                    member_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            LoanApplication dataModel= loanApplications.get(i);
                            dataModel.isActive();
                            loanApplicationAdapter.notifyDataSetChanged();

                        }
                    });


                  
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CGTProcessActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void  get_checked_ids(String app_no)
    {

        application_numbers.add(app_no);
    }
    public void get_checked_ids_pop(String app_no)
    {
        application_numbers.remove(app_no);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight(); //
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }
    public void get_groups_from_centers(int center_id)
    {
        new SyncFromServer(CGTProcessActivity.this).get_Groups(String.valueOf(center_id));
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        ExecutorService executor = Executors.newSingleThreadExecutor();

                        executor.execute(() -> {
//                            ListGroup =   AppDatabase.getDatabase(CGTProcessActivity.this).groupDao().get_group_by_centers(center_id);
                            if(ListGroup.size() > 0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        group_name.setAdapter(new HintSpinnerAdapter<Group>(CGTProcessActivity.this, ListGroup, "Select Group") {
                                            @Override
                                            public String getLabelFor(Group group) {
                                                return group.getCenter_name()+"-"+group.getGroup_name();
                                            }

                                        });
                                    }
                                });
                            }


                            group_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    group_id = ListGroup.get(i).getGroup_id();

                                    get_group_members(center_id,ListGroup.get(i).getGroup_id());
                                    //Toast.makeText(CGTProcessActivity.this, ""+ListGroup.get(i).getGroup_id(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        });
                    }
                },
                1000);
    }

    public void load_details()
    {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {

                        for (int j = 0; j < AreaList.size(); j++) {
                            // Toast.makeText(LoanActivityOne.this, ""+ListGroup.get(j).getGroup_id()+""+group_id_get, Toast.LENGTH_SHORT).show();
                            if (AreaList.get(j).getArea_id() == getIntent().getIntExtra("area_id",0)) {
                                area_name.setSelection(j+1);

                            }
                        }

                        for (int j = 0; j < centerList.size(); j++) {
                            // Toast.makeText(LoanActivityOne.this, ""+ListGroup.get(j).getGroup_id()+""+group_id_get, Toast.LENGTH_SHORT).show();
                            if (centerList.get(j).getCenter_id() == getIntent().getIntExtra("center_id",0)) {
                                center_name.setSelection(j+1);

                            }
                        }

                    }
                },
                1000);
    }
    public void insertRowToserver(long last_inserted_id)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<CGT> CGT =  new ArrayList<>();
            CGT =   AppDatabase.getDatabase(CGTProcessActivity.this).cgtDao().get_single_record(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),last_inserted_id);

            if(CGT.size()>0) {
                SubmitServer(CGT);
            }

        });
    }

    public void uploadCGTimage()
    {
        File file = new File(imageUri.getPath());
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), Image_name);
        Map<String, RequestBody> map = new HashMap<>();
        map.put("file\"; filename=\""+Image_name+"\" ", fbody);
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
        Call<Result> call = service.SubmitCGTImage(map);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("Uploaded", String.valueOf(response.body().getMessage()));

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(CGTProcessActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void SubmitServer(List<CGT> list)
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
        Call<Result> call = service.SubmitCGTRow(list);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Toast.makeText(CGTProcessActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
               // Log.d("responseinsert", String.valueOf(response.body().getData()));
              //  Log.d("responseinsert", String.valueOf(response.body().getMessage()));
                uploadCGTimage();
                Intent intent = new Intent(CGTProcessActivity.this, CGTViewActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(CGTProcessActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            imageUri = data.getData();
            pr.setImageURI(fileUri);
            upload_img = true;

            BitmapDrawable drawable = (BitmapDrawable) pr.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            try {

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/CGT");
                if (!file.exists()) {
                    file.mkdirs();
                }
                Image_name = UUID.randomUUID().toString()+".jpg";
                FileOutputStream out = new FileOutputStream(file+"/"+Image_name);
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                out.flush();
                out.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}