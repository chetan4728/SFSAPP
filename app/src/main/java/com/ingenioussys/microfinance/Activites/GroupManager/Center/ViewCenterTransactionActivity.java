package com.ingenioussys.microfinance.Activites.GroupManager.Center;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ingenioussys.microfinance.Activites.GroupManager.Groups.ViewGroupTransactionActivity;
import com.ingenioussys.microfinance.Adapter.CenterTransactionAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.ingenioussys.microfinance.utility.SyncToServer;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
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

public class ViewCenterTransactionActivity extends AppCompatActivity {

    PrefManager prefManager;
    ListView centerList;
    List<Center> centerArrayList;
    ProgressDialog progressDialog;
    TextView no_data;
    RadioButton radioButton;
    int selected_verification_status = 0;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_verfication);
        getSupportActionBar().setTitle("Center Transactions");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressDialog  =  new ProgressDialog(this);
        progressDialog.setMessage("Loading Data ...");
        prefManager =  new PrefManager(this);
        no_data =  findViewById(R.id.no_data);
        centerList = findViewById(R.id.centerList);
        new SyncFromServer(this).get_centers();
        new SyncToServer(this).UploadCenter();
        LoadCenterData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.file_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.add_survey) {
            Intent intent =  new Intent(ViewCenterTransactionActivity.this, CreateCenterActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);





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
                        Toast.makeText(ViewCenterTransactionActivity.this, ""+centerArrayList.size(), Toast.LENGTH_SHORT).show();

                        CenterTransactionAdapter surveyAdapter =  new CenterTransactionAdapter(ViewCenterTransactionActivity.this,centerArrayList);
                        centerList.setAdapter(surveyAdapter);
                        centerList.setVisibility(View.VISIBLE);
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
    public void ViewGroupDetail(int id)
    {
        Toast.makeText(this, ""+centerArrayList.get(id).getCenter_no(), Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(ViewCenterTransactionActivity.this, ViewGroupTransactionActivity.class);
        Bundle bundle=  new Bundle();
        bundle.putString("center_id", String.valueOf(centerArrayList.get(id).getCenter_no()));
        bundle.putString("branch_id", String.valueOf(centerArrayList.get(id).getBranch_id()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ViewCenterDetail(int id)
    {

        Intent intent =  new Intent(ViewCenterTransactionActivity.this, CreateCenterActivity.class);
        Bundle bundle=  new Bundle();
        bundle.putString("center_id", String.valueOf(centerArrayList.get(id).getCenter_id()));
        bundle.putString("branch_id", String.valueOf(centerArrayList.get(id).getBranch_id()));
        intent.putExtras(bundle);
        startActivity(intent);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}