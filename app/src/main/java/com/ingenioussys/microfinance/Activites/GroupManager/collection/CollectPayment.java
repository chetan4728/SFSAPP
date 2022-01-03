package com.ingenioussys.microfinance.Activites.GroupManager.collection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class CollectPayment extends AppCompatActivity {

    TextView applicant_name,applicant_email,applicant_no,applicant_add,loan_no,bank_name,area_name,branch_name,center_name,group_name;
    TextView loan_amount,monthly_emi,op_bal,closing_bal,emi_date;
    TextView emi_no,emi_lbl,submit_emi,payment_status,payment_date;
    EditText get_emi;
    PrefManager prefManager;
    ImageView profile_image;
    ProgressDialog progressDialog;
    String loan_distribution_emi_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_payment);
        getSupportActionBar().setTitle(getIntent().getStringExtra("loan_application_no"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager =  new PrefManager(this);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        submit_emi = findViewById(R.id.submit_emi);
        get_emi = findViewById(R.id.get_emi);
        payment_date  = findViewById(R.id.payment_date);
        payment_status  = findViewById(R.id.payment_status);
        applicant_name = findViewById(R.id.applicant_name);
        applicant_email = findViewById(R.id.applicant_email);
        applicant_no = findViewById(R.id.applicant_no);
        applicant_add = findViewById(R.id.applicant_add);
        loan_no = findViewById(R.id.loan_no);
        bank_name = findViewById(R.id.bank_name);
        area_name = findViewById(R.id.area_name);
        branch_name = findViewById(R.id.branch_name);
        center_name = findViewById(R.id.center_name);
        group_name = findViewById(R.id.group_name);
        loan_amount = findViewById(R.id.loan_amount);
        monthly_emi = findViewById(R.id.monthly_emi);
        closing_bal = findViewById(R.id.closing_bal);
        op_bal = findViewById(R.id.op_bal);
        emi_date = findViewById(R.id.emi_date);
        emi_no = findViewById(R.id.emi_no);
        profile_image  = findViewById(R.id.profile_image);
        emi_lbl = findViewById(R.id.emi_lbl);

      //  Toast.makeText(this, ""+getIntent().getStringExtra("loan_application_no"), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, ""+this.getIntent().getExtras().getString("emi_id"), Toast.LENGTH_SHORT).show();
        getLoanInformation();

        submit_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(get_emi.getText().toString().isEmpty())
                {
                    get_emi.setError("Please Enter Amount");
                    get_emi.requestFocus();
                }

                else
                {
                    int  emi_ = Integer.parseInt(get_emi.getText().toString());
                    int  emi_lbl_ = Integer.parseInt(emi_lbl.getTag().toString());
                     if(emi_ > emi_lbl_)
                        {
                            get_emi.setError("Please Enter Amount less than EMI");
                            get_emi.requestFocus();
                        }
                         else
                         {
                             UpdateEmiData();
                         }

                }


            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void  UpdateEmiData()
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
        Call<Result> call = service.update_emi_amount(this.getIntent().getExtras().getString("loan_application_no"),loan_distribution_emi_id,get_emi.getText().toString());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                progressDialog.dismiss();
                Toast.makeText(CollectPayment.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();



            }



            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(CollectPayment.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getLoanInformation()
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
        Call<Result> call = service.get_loan_emi_details(this.getIntent().getExtras().getString("loan_application_no"),this.getIntent().getExtras().getString("emi_id"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {




                try {
                    JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                    Log.d("loan_emi",jsonarray.toString());
                    applicant_name.setText(jsonarray.getJSONObject(0).getString("applicant_name"));
                    applicant_no.setText(jsonarray.getJSONObject(0).getString("applicant_mob_no"));
                    applicant_email.setText(jsonarray.getJSONObject(0).getString("email_id"));
                    applicant_add.setText(jsonarray.getJSONObject(0).getString("address"));
                    loan_no.setText(jsonarray.getJSONObject(0).getString("laon_application_no"));
                    bank_name.setText(jsonarray.getJSONObject(0).getString("bank_name"));
                    area_name.setText(jsonarray.getJSONObject(0).getString("area_name"));
                    branch_name.setText(jsonarray.getJSONObject(0).getString("branch_name"));
                    center_name.setText(jsonarray.getJSONObject(0).getString("center_name"));
                    group_name.setText(jsonarray.getJSONObject(0).getString("group_name"));
                    loan_amount.setText("₹"+jsonarray.getJSONObject(0).getString("total_loan_amount"));
                    monthly_emi.setText("₹"+jsonarray.getJSONObject(0).getString("scheduled_payment"));
                    emi_date.setText(jsonarray.getJSONObject(0).getString("inc_date"));
                    op_bal.setText("₹"+jsonarray.getJSONObject(0).getString("begining_bal"));
                    closing_bal.setText("₹"+jsonarray.getJSONObject(0).getString("ending_balance"));
                    emi_no.setText("EMI NO "+jsonarray.getJSONObject(0).getString("emi_no"));
                    emi_lbl.setText("₹"+jsonarray.getJSONObject(0).getString("scheduled_payment"));
                    emi_lbl.setTag(jsonarray.getJSONObject(0).getString("scheduled_payment"));

                    loan_distribution_emi_id = jsonarray.getJSONObject(0).getString("loan_distribution_emi_id");
                    if(jsonarray.getJSONObject(0).getInt("status")==0)
                    {
                        payment_status.setText("Pending");
                    }
                    else
                    {
                        payment_status.setText("paid");
                    }
                    payment_date.setText(jsonarray.getJSONObject(0).getString("paid_date"));
                    Glide.with(CollectPayment.this).load(jsonarray.getJSONObject(0).getString("member_photo_pr")).into(profile_image);
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();



            }



            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(CollectPayment.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}