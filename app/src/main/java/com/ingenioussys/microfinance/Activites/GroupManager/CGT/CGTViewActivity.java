package com.ingenioussys.microfinance.Activites.GroupManager.CGT;

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

import com.ingenioussys.microfinance.Adapter.CGTAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.CGT;
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

public class CGTViewActivity extends AppCompatActivity {
    Activity activity;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    ListView list;
    ArrayList<CGT> surveyArrayList;
    TextView no_data;
    List<CGT> cgtList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_g_t_view);
        getSupportActionBar().setTitle("My CGT");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list =  findViewById(R.id.list);
        no_data =  findViewById(R.id.no_data);
        activity =  this;
        prefManager =  new PrefManager(this);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Loading Data ...");
        //new SyncFromServer(this).get_cgt();
        LoadSurveys();
    }




    public void ViewCGTDetail(int id)
    {
        Intent intent =  new Intent(CGTViewActivity.this, CGTProcessActivity.class);
        Bundle bundle=  new Bundle();
        bundle.putInt("center_id", cgtList.get(id).getCenter_id());
        bundle.putInt("area_id", cgtList.get(id).getArea_id());
        bundle.putInt("cgt_id", cgtList.get(id).getCgt_id());
        bundle.putInt("process_id",cgtList.get(id).getProcess_id());

        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void LoadSurveys()
    {
        cgtList =  new ArrayList<>();
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

                            cgtList.add(cgt);
                        }


                        CGTAdapter cgtAdapter =  new CGTAdapter(CGTViewActivity.this,cgtList);
                        list.setAdapter(cgtAdapter);
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


//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        AsyncTask.execute(() -> {
//
//
//                            cgtList =  new ArrayList<>();
//                            cgtList =   AppDatabase.getDatabase(getApplicationContext()).cgtDao().getAll();
//
//                            if(cgtList.size()>0) {
//
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        CGTAdapter cgtAdapter =  new CGTAdapter(CGTViewActivity.this,cgtList);
//                                        list.setAdapter(cgtAdapter);
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

    }
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
            Intent intent =  new Intent(CGTViewActivity.this, CGTProcessActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);





    }
}