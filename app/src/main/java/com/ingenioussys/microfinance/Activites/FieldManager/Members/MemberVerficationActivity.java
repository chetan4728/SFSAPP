package com.ingenioussys.microfinance.Activites.FieldManager.Members;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ingenioussys.microfinance.R;

public class MemberVerficationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_verfication);
        getSupportActionBar().setTitle("Member Verification");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}