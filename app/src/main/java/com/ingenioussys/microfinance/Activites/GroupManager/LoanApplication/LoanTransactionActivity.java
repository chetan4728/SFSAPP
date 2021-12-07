package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.FormFragment.LoanActivityOne;
import com.ingenioussys.microfinance.Adapter.LoanApplicationAdapter;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.ingenioussys.microfinance.utility.PrefManager;

import java.util.ArrayList;
import java.util.List;

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
        load_Loan_transactions_list();
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