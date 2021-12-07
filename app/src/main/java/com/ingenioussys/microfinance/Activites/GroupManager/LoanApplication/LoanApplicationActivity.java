package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ingenioussys.microfinance.Activites.GroupManager.Center.ViewCenterTransactionActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.Groups.ViewGroupTransactionActivity;
import com.ingenioussys.microfinance.R;

public class LoanApplicationActivity extends AppCompatActivity {

    CardView createLoanApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        createLoanApplication = findViewById(R.id.createLoanApplication);
        getSupportActionBar().setTitle("Acquisition");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        createLoanApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanApplicationActivity.this, LoanTransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void createCenter(View view) {
        Intent intent = new Intent(LoanApplicationActivity.this, ViewCenterTransactionActivity.class);
        startActivity(intent);
    }

    public void createGroup(View view) {

        Intent intent = new Intent(LoanApplicationActivity.this, ViewGroupTransactionActivity.class);
        startActivity(intent);
    }
}