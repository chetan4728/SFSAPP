package com.ingenioussys.microfinance.Activites.GroupManager.Members;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ingenioussys.microfinance.Adapter.LoanApplicationAdapter;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.utility.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class ViewMemberActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    PrefManager prefManager;
    List<LoanApplication> loanApplications;
    ListView LoanApplicationList;
    TextView no_data;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member);
        getSupportActionBar().setTitle("View Members");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        no_data =  findViewById(R.id.no_data);
        LoanApplicationList =  findViewById(R.id.LoanApplicationList);
        activity =  this;
        prefManager =  new PrefManager(this);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Loading Data ...");


  load_Loan_transactions_list(Integer.parseInt(getIntent().getStringExtra("group_id")));
    }

    public void load_Loan_transactions_list(int group_id)
    {

        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        AsyncTask.execute(() -> {


                            loanApplications =  new ArrayList<>();
                            loanApplications =   AppDatabase.getDatabase(getApplicationContext()).loanApplicationDao().getAllByGroup(Integer.parseInt(prefManager.getString("employee_id")), Integer.parseInt(prefManager.getString("branch_id")), group_id);

                            if(loanApplications.size()>0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoanApplicationAdapter applicationAdapter =  new LoanApplicationAdapter(ViewMemberActivity.this,loanApplications);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}