package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication;

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

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment.LoanActivityOne;
import com.ingenioussys.microfinance.Adapter.LoanApplicationAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;

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

public class LoanTransactionActivity extends AppCompatActivity {
    Activity activity;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    List<LoanApplication> loanApplications;
    ListView LoanApplicationList;
    TextView no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_transaction);
        getSupportActionBar().setTitle("Loan Transactions");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        no_data =  findViewById(R.id.no_data);
        LoanApplicationList =  findViewById(R.id.LoanApplicationList);
        activity =  this;
        prefManager =  new PrefManager(this);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Loading Data ...");
        load_loan_list();
    }

    public void load_loan_list()
    {

            loanApplications =  new ArrayList<>();
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
            Call<Result> call = service.LoadLoanApplications(prefManager.getString("bank_id"),prefManager.getString("employee_id"));
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.body().getError()) {
                        Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {

                    //    Log.d("loans",response.body().getData().toString());


                        try {
                            JSONArray jsonarray = new JSONArray(response.body().getData().toString());
                            //Log.d("loansdd",jsonarray.toString());

                            for(int i=0; i < jsonarray.length() ; i++)
                            {
                               // Toast.makeText(LoanTransactionActivity.this, ""+jsonarray.getJSONObject(i).getString("loan_application_number"), Toast.LENGTH_SHORT).show();
                                LoanApplication loanApplication =  new LoanApplication();

                                loanApplication.setLoan_application_number(jsonarray.getJSONObject(i).getString("loan_application_number"));
                                loanApplication.setApplicant_name(jsonarray.getJSONObject(i).getString("applicant_name"));
                                loanApplication.setApproved_status(jsonarray.getJSONObject(i).getInt("is_verification"));
                                loanApplication.setMember_photo_pr(jsonarray.getJSONObject(i).getString("member_photo_pr"));

                                loanApplications.add(loanApplication);


                            }
                            //Toast.makeText(LoanTransactionActivity.this, ""+loanApplications.size(), Toast.LENGTH_SHORT).show();

                            LoanApplicationAdapter applicationAdapter =  new LoanApplicationAdapter(LoanTransactionActivity.this,loanApplications);
                            LoanApplicationList.setAdapter(applicationAdapter);
                            LoanApplicationList.setVisibility(View.VISIBLE);
                            no_data.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void load_Loan_transactions_list()
    {

        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        AsyncTask.execute(() -> {


                            loanApplications =  new ArrayList<>();
                            loanApplications =   AppDatabase.getDatabase(getApplicationContext()).loanApplicationDao().getAll();

                            if(loanApplications.size()>0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoanApplicationAdapter applicationAdapter =  new LoanApplicationAdapter(LoanTransactionActivity.this,loanApplications);
                                        LoanApplicationList.setAdapter(applicationAdapter);
                                        LoanApplicationList.setVisibility(View.VISIBLE);
                                         no_data.setVisibility(View.GONE);
                                    }
                                });
                            }
                            else
                            {
                                LoanApplicationList.setVisibility(View.GONE);
                                no_data.setVisibility(View.VISIBLE);
                            }
                            progressDialog.dismiss();

                        });
                    }
                },
                2000);
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

    public  void viewLoanApplication(String loan_id)
    {
        Intent intent =  new Intent(LoanTransactionActivity.this, LoanActivityOne.class);
        Bundle bundle =  new Bundle();
        bundle.putString("loan_application_number",loan_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.add_survey) {
            Intent intent =  new Intent(LoanTransactionActivity.this, LoanActivityOne.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);





    }
}