package com.ingenioussys.microfinance.Activites.GroupManager.collection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Adapter.LoanEmiAdapter;
import com.ingenioussys.microfinance.Adapter.MyAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Item;
import com.ingenioussys.microfinance.model.LoanEmi;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.multilevelview.models.RecyclerViewItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ingenioussys.microfinance.constant.Global.SERVER_URL;

public class EmiListing extends AppCompatActivity {
   ArrayList<LoanEmi>  loanEmi;
    PrefManager prefManager;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_listing);
        getSupportActionBar().setTitle(getIntent().getStringExtra("loan_application_no"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager =  new PrefManager(this);
        listview=  findViewById(R.id.listview);
        get_emi_listing();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void get_emi_listing()
    {
        loanEmi =  new ArrayList<>();


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
        Call<Result> call = service.get_loan_emi_listing(getIntent().getStringExtra("loan_application_no"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {




                try {
                    JSONArray jsonarray = new JSONArray(response.body().getData().toString());

                    Log.d("araaaa",jsonarray.toString());
                  if(jsonarray.length()>0) {
                      for (int i = 0; i < jsonarray.length(); i++) {
                          LoanEmi emi = new LoanEmi();
                          emi.setLoan_distribution_emi_id(jsonarray.getJSONObject(i).getInt("loan_distribution_emi_id"));
                          emi.setEmi_no(jsonarray.getJSONObject(i).getString("emi_no"));
                          emi.setLaon_application_no(jsonarray.getJSONObject(i).getString("laon_application_no"));
                          emi.setInc_date(jsonarray.getJSONObject(i).getString("inc_date"));
                          emi.setScheduled_payment(jsonarray.getJSONObject(i).getString("scheduled_payment"));
                          emi.setStatus(jsonarray.getJSONObject(i).getInt("status"));
                          loanEmi.add(emi);
                      }

                      LoanEmiAdapter loanEmiAdapter = new LoanEmiAdapter(loanEmi, EmiListing.this);
                      listview.setAdapter(loanEmiAdapter);
                  }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }



            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(EmiListing.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}