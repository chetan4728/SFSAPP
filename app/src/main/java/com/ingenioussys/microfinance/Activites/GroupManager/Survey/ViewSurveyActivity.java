package com.ingenioussys.microfinance.Activites.GroupManager.Survey;

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

import com.ingenioussys.microfinance.Adapter.SurveyAdapter;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Survey;
import com.ingenioussys.microfinance.utility.PrefManager;
import com.ingenioussys.microfinance.utility.SyncFromServer;

import java.util.ArrayList;
import java.util.List;

public class ViewSurveyActivity extends AppCompatActivity {
    Activity activity;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    ListView list;
    ArrayList<Survey> surveyArrayList;
    TextView no_data;
    List<Survey> SurveyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_survey);
        getSupportActionBar().setTitle("My Surveys");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list =  findViewById(R.id.list);
        no_data =  findViewById(R.id.no_data);
        activity =  this;
        prefManager =  new PrefManager(this);
        progressDialog  =  new ProgressDialog(activity);
        progressDialog.setMessage("Loading Data ...");
        new SyncFromServer(this).getSurveys();
        LoadSurveys();
    }



    public void LoadSurveys()
    {
        surveyArrayList =  new ArrayList<>();
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        AsyncTask.execute(() -> {


                            Log.d("details",prefManager.getString("employee_id")+"--"+prefManager.getString("branch_id"));

                            SurveyList =  new ArrayList<>();
//                            SurveyList =   AppDatabase.getDatabase(getApplicationContext()).surveyDao().getAll(Integer.parseInt(prefManager.getString("employee_id")),Integer.parseInt(prefManager.getString("branch_id")));

                            if(SurveyList.size()>0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SurveyAdapter surveyAdapter =  new SurveyAdapter(ViewSurveyActivity.this,SurveyList);
                                        list.setAdapter(surveyAdapter);
                                        list.setVisibility(View.VISIBLE);
                                        no_data.setVisibility(View.GONE);
                                    }
                                });
                            }
                            else
                            {
                                list.setVisibility(View.GONE);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.add_survey) {
                Intent intent =  new Intent(ViewSurveyActivity.this, ManageSurveyActivity.class);
                startActivity(intent);
                finish();
               return true;
            }

        return super.onOptionsItemSelected(item);





    }
}