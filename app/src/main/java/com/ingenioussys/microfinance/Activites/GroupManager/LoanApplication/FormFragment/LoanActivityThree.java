package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.LoanApplicationCashTwoFlow;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;

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

public class LoanActivityThree extends AppCompatActivity {

    EditText no_of_dep,house_hold_income,current_profession,lottery,spouse_name,income_of_this_month;
    Button back,next;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    Boolean update_flag = false;
    long cash_flow_two_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_three);
        getSupportActionBar().setTitle("Cash Flow Part 2");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager =  new PrefManager(this);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Submitting Data...");
        no_of_dep  =findViewById(R.id.no_of_dep);
        house_hold_income  =findViewById(R.id.house_hold_income);
        current_profession  =findViewById(R.id.current_profession);
        lottery  =findViewById(R.id.lottery);
        spouse_name  =findViewById(R.id.spouse_name);
        income_of_this_month  =findViewById(R.id.income_of_this_month);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        load_form_two_data();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update_flag)
                {
                    UpdateForm();
                }
                else
                {
                    nextForm();
                }

            }
        });
    }
    public void load_form_two_data()
    {

        AsyncTask.execute(() -> {
            List<LoanApplicationCashTwoFlow> LoanApplications = new ArrayList<>();
            LoanApplications = AppDatabase.getDatabase(LoanActivityThree.this).loanApplicationCashFlowTwoDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")), Integer.parseInt(prefManager.getString("branch_id")), getIntent().getStringExtra("loan_application_no"));
            Log.d("dddddsd", String.valueOf(LoanApplications.size()));
            if(LoanApplications.size() > 0) {
                update_flag = true;
                cash_flow_two_id = LoanApplications.get(0).getLoan_application_cash_flow_two_id();
                no_of_dep.setText(LoanApplications.get(0).getNo_of_dep());
                current_profession.setText(LoanApplications.get(0).getCurrent_profession());
                house_hold_income.setText(LoanApplications.get(0).getHouse_hold_income());
                lottery.setText(LoanApplications.get(0).getLottery());
                spouse_name.setText(LoanApplications.get(0).getSpouse_name());
                income_of_this_month.setText(LoanApplications.get(0).getIncome_of_this_month());
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public  void  nextForm()
    {
        if(no_of_dep.getText().toString().isEmpty())
        {
            no_of_dep.setError("Enter No of Department");
            no_of_dep.requestFocus();
        }
        else if(house_hold_income.getText().toString().isEmpty())
        {
            house_hold_income.setError("Enter House Hold Income");
            house_hold_income.requestFocus();
        }
        else if(current_profession.getText().toString().isEmpty())
        {
            current_profession.setError("Enter Current Profession");
            current_profession.requestFocus();
        }
        else if(lottery.getText().toString().isEmpty())
        {
            lottery.setError("Enter Lottery Price");
            lottery.requestFocus();
        }
        else if(spouse_name.getText().toString().isEmpty())
        {
            spouse_name.setError("Enter Spouse Name");
            spouse_name.requestFocus();
        }
        else if(income_of_this_month.getText().toString().isEmpty())
        {
            income_of_this_month.setError("Enter Income Of this month");
            income_of_this_month.requestFocus();
        }

        else {
            LoanApplicationCashTwoFlow loanApplication =  new LoanApplicationCashTwoFlow();
            loanApplication.setLoan_application_number(getIntent().getStringExtra("loan_application_no"));
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setLoan_application_cash_flow_two_id((int) cash_flow_two_id);
            loanApplication.setLoan_application_id((int) getIntent().getLongExtra("id", 0));
            loanApplication.setNo_of_dep(no_of_dep.getText().toString());
            loanApplication.setHouse_hold_income(house_hold_income.getText().toString());
            loanApplication.setCurrent_profession(current_profession.getText().toString());
            loanApplication.setLottery(lottery.getText().toString());
            loanApplication.setSpouse_name(spouse_name.getText().toString());
            loanApplication.setIncome_of_this_month(income_of_this_month.getText().toString());
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(LoanActivityThree.this).loanApplicationCashFlowTwoDao().insert(loanApplication);
                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Intent intent =  new Intent(LoanActivityThree.this,LoanActivityFour.class);
                                                        Bundle bundle =  new Bundle();
                                                        insertRowToServer(getIntent().getLongExtra("id",0));
                                                        bundle.putLong("id",getIntent().getLongExtra("id",0));
                                                        bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
                                                        bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                    }
                                                },
                                                1000);
                                    }
                                });



                            });
                        }
                    },
                    2000);

        }
    }
    public  void  UpdateForm()
    {
        if(no_of_dep.getText().toString().isEmpty())
        {
            no_of_dep.setError("Enter No of Department");
            no_of_dep.requestFocus();
        }
        else if(house_hold_income.getText().toString().isEmpty())
        {
            house_hold_income.setError("Enter House Hold Income");
            house_hold_income.requestFocus();
        }
        else if(current_profession.getText().toString().isEmpty())
        {
            current_profession.setError("Enter Current Profession");
            current_profession.requestFocus();
        }
        else if(lottery.getText().toString().isEmpty())
        {
            lottery.setError("Enter Lottery Price");
            lottery.requestFocus();
        }
        else if(spouse_name.getText().toString().isEmpty())
        {
            spouse_name.setError("Enter Spouse Name");
            spouse_name.requestFocus();
        }
        else if(income_of_this_month.getText().toString().isEmpty())
        {
            income_of_this_month.setError("Enter Income Of this month");
            income_of_this_month.requestFocus();
        }

        else {
            LoanApplicationCashTwoFlow loanApplication =  new LoanApplicationCashTwoFlow();
            loanApplication.setLoan_application_cash_flow_two_id((int) cash_flow_two_id);
            loanApplication.setLoan_application_number(getIntent().getStringExtra("loan_application_no"));
            loanApplication.setLoan_application_id((int) getIntent().getLongExtra("id", 0));
            loanApplication.setNo_of_dep(no_of_dep.getText().toString());
            loanApplication.setHouse_hold_income(house_hold_income.getText().toString());
            loanApplication.setCurrent_profession(current_profession.getText().toString());
            loanApplication.setLottery(lottery.getText().toString());
            loanApplication.setSpouse_name(spouse_name.getText().toString());
            loanApplication.setIncome_of_this_month(income_of_this_month.getText().toString());
            loanApplication.setBank_id(Integer.parseInt(prefManager.getString("bank_id")));
            loanApplication.setBranch_id(Integer.parseInt(prefManager.getString("branch_id")));
            loanApplication.setCreated_by(Integer.parseInt(prefManager.getString("employee_id")));

            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            AsyncTask.execute(() -> {
                                AppDatabase.getDatabase(LoanActivityThree.this).loanApplicationCashFlowTwoDao().update(loanApplication);
                                // Log.d("last_inserted_id", String.valueOf(last_inserted_id));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                        Intent intent =  new Intent(LoanActivityThree.this,LoanActivityFour.class);
                                                        Bundle bundle =  new Bundle();
                                                        UpdateRowToServer(getIntent().getLongExtra("id",0));
                                                        bundle.putLong("id",getIntent().getLongExtra("id",0));
                                                        bundle.putString("loan_application_no",getIntent().getStringExtra("loan_application_no"));
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                    }
                                                },
                                                1000);
                                    }
                                });



                            });
                        }
                    },
                    2000);

        }
    }

    public void UpdateRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplicationCashTwoFlow> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityThree.this).loanApplicationCashFlowTwoDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_no"));


            if(LoanApplications.size()>0) {
                UpdateToserver(LoanApplications);
            }

        });


    }
    public void insertRowToServer(long last_inserted_id)
    {
        AsyncTask.execute(() -> {
            List<LoanApplicationCashTwoFlow> LoanApplications =  new ArrayList<>();
            LoanApplications =   AppDatabase.getDatabase(LoanActivityThree.this).loanApplicationCashFlowTwoDao().getSingle(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")),getIntent().getStringExtra("loan_application_no"));
            //  Log.d("sdfsdfsdfsd",LoanApplications.get(0).getApplicant_name());
            if(LoanApplications.size()>0) {
                SubmitSurveyServer(LoanApplications,last_inserted_id);
            }

        });


    }
    public void SubmitSurveyServer(List<LoanApplicationCashTwoFlow> loanApplication,long last_inserted_id)
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
        Call<Result> call = service.UpdatecashflowTwoRow(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //  Log.d("testadfasdf", response.message());
                // Log.d("loanMe", String.valueOf(response.body().getMessage()));

                Toast.makeText(LoanActivityThree.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                //  ((LoanApplicationForm) getActivity()).selectIndex(1,loanApplication.get(0).getLoan_application_id());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityThree.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }
    public void UpdateToserver(List<LoanApplicationCashTwoFlow> loanApplication)
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
        Call<Result> call = service.UpdatecashflowTwoRow(loanApplication);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("testadfasdf", response.body().getMessage());
                Toast.makeText(LoanActivityThree.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanActivityThree.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Toast.makeText(getApplicationContext(), ""+branch_id, Toast.LENGTH_SHORT).show();

    }

}