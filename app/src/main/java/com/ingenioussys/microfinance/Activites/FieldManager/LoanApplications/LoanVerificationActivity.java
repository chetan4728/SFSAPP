package com.ingenioussys.microfinance.Activites.FieldManager.LoanApplications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.collection.CollectionActivity;
import com.ingenioussys.microfinance.Adapter.LoanApplicationVerificationListAdapter;
import com.ingenioussys.microfinance.Adapter.MyAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Item;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.model.Role;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.multilevelview.MultiLevelRecyclerView;
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

public class LoanVerificationActivity extends AppCompatActivity {

    PrefManager prefManager;
    ProgressDialog progressDialog;
    List<RecyclerViewItem> levelOne;
    List<RecyclerViewItem> levelTwo;
    List<RecyclerViewItem> levelThree;
    MultiLevelRecyclerView multiLevelRecyclerView;
    List<?> finalData;
    EditText searchBox;
    Button prevBtn,nextbtn,search;
    private int currentPage = 0;
    private  int limit = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_verification);
       // setContentView(R.layout.activity_collection);
        getSupportActionBar().setTitle("Loan Application Verification");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager =  new PrefManager(this);
        progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        prevBtn  = findViewById(R.id.prevBtn);
        nextbtn  = findViewById(R.id.nextbtn);
        search  = findViewById(R.id.search);
        searchBox  = findViewById(R.id.searchBox);

        multiLevelRecyclerView = (MultiLevelRecyclerView) findViewById(R.id.rv_list);
        multiLevelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        get_collection_data(currentPage,limit,searchBox.getText().toString());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(LoanVerificationActivity.this, "", Toast.LENGTH_SHORT).show();
                get_collection_data(currentPage,limit,searchBox.getText().toString());
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
                get_collection_data(currentPage,limit,searchBox.getText().toString());
                //Toast.makeText(CollectionActivity.this, ""+currentPage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<?> get_collection_data(int currentPage, int limit, String group_name )
    {
       // Toast.makeText(this, ""+prefManager.getString("bank_id")+"--"+prefManager.getString("branch_id"), Toast.LENGTH_SHORT).show();
        levelOne =  new ArrayList<>();
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
        Call<Result> call = service.get_loan_application_list(prefManager.getString("bank_id"),prefManager.getString("branch_id"),currentPage,limit, Integer.parseInt(prefManager.getString("employee_id")),group_name);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {


                Log.d("araaaa",response.body().getData().toString());

                try {
                    JSONArray jsonarray = new JSONArray(response.body().getData().toString());




                    int levelNumber = 0;
                    int depth = jsonarray.length();

                    if(depth > 0) {
                        for (int i = 0; i < depth; i++) {
                            Item item = new Item(levelNumber);
                            String center_name = jsonarray.getJSONObject(i).getString("branch_name") + " - " + jsonarray.getJSONObject(i).getString("center_name");
                            String sub_name = jsonarray.getJSONObject(i).getString("area_name");
                            item.setText(String.format(Locale.ENGLISH, center_name, i));
                            item.setSecondText(String.format(Locale.ENGLISH, sub_name, i));
                            JSONArray group_array = new JSONArray(jsonarray.getJSONObject(i).getJSONArray("group_data").toString());
                            levelTwo = new ArrayList<>();
                            for (int j = 0; j < group_array.length(); j++) {
                                Item item2 = new Item(1);
                                item2.setText(String.format(Locale.ENGLISH, group_array.getJSONObject(j).getString("group_name"), j));
                                item2.setSecondText(String.format(Locale.ENGLISH, "Limit - " + group_array.getJSONObject(j).getString("member_limit"), j));
                                levelTwo.add(item2);
                                JSONArray member_array = new JSONArray(group_array.getJSONObject(j).getJSONArray("member_data").toString());
                                levelThree = new ArrayList<>();
                                for (int m = 0; m < member_array.length(); m++) {
                                    Item item3 = new Item(2);


                                    item3.setText(String.format(Locale.ENGLISH, member_array.getJSONObject(m).getString("applicant_name"), m));
                                    item3.setSecondText(String.format(Locale.ENGLISH, member_array.getJSONObject(m).getString("loan_application_number"), m));
                                    item3.setLoan_application_no(member_array.getJSONObject(m).getString("loan_application_number"));
                                    String is_verified="";

                                    if(member_array.getJSONObject(m).getInt("is_cgt_verfied")==1)
                                    {
                                        is_verified = "CGT - Approved";
                                    }
                                    else
                                    {
                                        is_verified = "CGT - DisApproved";
                                    }



                                    item3.setThirdText(String.format(Locale.ENGLISH, is_verified, m));
                                    levelThree.add(item3);

                                }
                                item2.addChildren((List<RecyclerViewItem>) levelThree);
                            }
                            item.addChildren((List<RecyclerViewItem>) levelTwo);

                            levelOne.add(item);

                        }
                        finalData = levelOne;
                        List<Item> finalDatalist = (List<Item>) finalData;
                        //Log.d("json_data", String.valueOf(levelOne.size()));
                        LoanApplicationVerificationListAdapter myAdapter = new LoanApplicationVerificationListAdapter(LoanVerificationActivity.this, finalDatalist, multiLevelRecyclerView);
                        multiLevelRecyclerView.setAdapter(myAdapter);
                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                // Toast.makeText(TakeSurveyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();



            }



            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(LoanVerificationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return levelOne;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}