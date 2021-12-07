package com.ingenioussys.microfinance.Activites.FieldManager.Center;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.FieldManager.Group.GroupVerificationActivity;
import com.ingenioussys.microfinance.Adapter.CenterVerificationAdapter;
import com.ingenioussys.microfinance.ApiServices.APIService;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Result;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;
import com.ingenioussys.microfinance.utility.SyncToServer;

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

public class CenterVerficationActivity extends AppCompatActivity {

    PrefManager prefManager;
    ListView centerList;
    List<Center> centerArrayList;
    ProgressDialog progressDialog;
    TextView no_data;
    RadioButton radioButton;
    int selected_verification_status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_verfication);
        getSupportActionBar().setTitle("Center Verification");
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

    public void LoadCenterData()
    {

//        centerArrayList =  new ArrayList<>();
//        progressDialog.show();
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        AsyncTask.execute(() -> {
//
//                            centerArrayList =   AppDatabase.getDatabase(getApplicationContext()).centerDao().getCenterByBranch(Integer.parseInt(prefManager.getString("branch_id")));
//                            if(centerArrayList.size()>0) {
//
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        CenterVerificationAdapter surveyAdapter =  new CenterVerificationAdapter(CenterVerficationActivity.this,centerArrayList);
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
        Intent intent =  new Intent(CenterVerficationActivity.this, GroupVerificationActivity.class);
        Bundle bundle=  new Bundle();
        bundle.putString("center_id", String.valueOf(centerArrayList.get(id).getCenter_id()));
        bundle.putString("branch_id", String.valueOf(centerArrayList.get(id).getBranch_id()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ViewCenterDetail(int id)
    {
        final Dialog dialog = new Dialog(CenterVerficationActivity.this);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);




        dialog.setContentView(R.layout.verify_center_diloge);
        ImageView close = dialog.findViewById(R.id.close);
        TextView fs_name =  dialog.findViewById(R.id.fs_name);
        TextView fs_id =  dialog.findViewById(R.id.fs_id);
        TextView branch_id =  dialog.findViewById(R.id.branch_id);
        TextView date_time =  dialog.findViewById(R.id.date_time);
        Button submit =  dialog.findViewById(R.id.submit);
        fs_name.setText("Created By : "+centerArrayList.get(id).getFs_name());
        fs_id.setText("FS Id :"+centerArrayList.get(id).getCreated_by());
        branch_id.setText("Branch Id : "+centerArrayList.get(id).getBranch_id());
        date_time.setText("Date Time : "+centerArrayList.get(id).getCreated_date());

        RadioGroup approval   = dialog.findViewById(R.id.approval);
        RadioButton approved   = dialog.findViewById(R.id.approved);
        RadioButton rejected   = dialog.findViewById(R.id.rejected);
        RadioButton pending   = dialog.findViewById(R.id.pending);

        approval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.approved:
                        // do operations specific to this selection
                        selected_verification_status = 1;
                        break;
                    case R.id.rejected:
                        // do operations specific to this selection
                        selected_verification_status = 2;
                        break;
                    case R.id.pending:
                        // do operations specific to this selection
                        selected_verification_status = 0;
                        break;
                }
            }
        });



        if(centerArrayList.get(id).getCenter_status()==1)
        {
            approved.setChecked(true);
        }
        else if(centerArrayList.get(id).getCenter_status()==2)
        {
            rejected.setChecked(true);
        }
        else if(centerArrayList.get(id).getCenter_status()==0)
        {
            pending.setChecked(true);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              if(selected_verification_status==0)
              {
                  Toast.makeText(CenterVerficationActivity.this, "Please Select Verification Status", Toast.LENGTH_SHORT).show();
              }
              else
              {
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
                  Call<Result> call = service.updateCenterverification(centerArrayList.get(id).getBranch_id(),centerArrayList.get(id).getCenter_id(),selected_verification_status);
                  call.enqueue(new Callback<Result>() {
                      @Override
                      public void onResponse(Call<Result> call, Response<Result> response) {
                          ///Log.d("response", String.valueOf(response.body().getData()));

                          Toast.makeText(CenterVerficationActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_LONG).show();
                          // progressDialog.dismiss();
                          dialog.dismiss();
                          new SyncFromServer(CenterVerficationActivity.this).get_centers();
                          LoadCenterData();
                      }
                      @Override
                      public void onFailure(Call<Result> call, Throwable t) {

                          Toast.makeText(CenterVerficationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                      }
                  });

              }

               // Toast.makeText(CenterVerficationActivity.this, ""+selected_verification_status, Toast.LENGTH_SHORT).show();
              //  Toast.makeText(CenterVerficationActivity.this, ""+radioButton.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}